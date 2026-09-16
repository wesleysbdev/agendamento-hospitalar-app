package br.com.fiap.agendamento.gerenciamento.application.consulta.usecases;

import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.out.AgendaRepository;
import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaEvent;
import br.com.fiap.agendamento.gerenciamento.application.consulta.enums.ConsultaEventType;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoCadastroConsulta;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaEventPublisher;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.HorarioAgenda;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.exception.ConsultaDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.exception.ConsultaNaoEncontradaException;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Medico;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Paciente;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CadastroConsultaUseCase implements GestaoCadastroConsulta {

    private final ConsultaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final AgendaRepository agendaRepository;
    private final ConsultaEventPublisher consultaEventPublisher;

    public CadastroConsultaUseCase(
            ConsultaRepository repository,
            UsuarioRepository usuarioRepository,
            AgendaRepository agendaRepository,
            ConsultaEventPublisher consultaEventPublisher
    ) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.agendaRepository = agendaRepository;
        this.consultaEventPublisher = consultaEventPublisher;
    }

    private static void validarHospital(Hospital hospital) {
        if (!hospital.isAtivo() || hospital.isExcluido()) {
            throw new ConsultaDadosInvalidosException("O hospital não pode receber atendimentos.");
        }
    }

    private static void validarMedico(Medico medico) {
        if (!medico.isAtivo() || medico.isExcluido()) {
            throw new ConsultaDadosInvalidosException("Médico não pode realizar atendimentos.");
        }
    }

    private static void validarPaciente(Paciente paciente, UsuarioAutenticado usuarioAutenticado) {
        if (!paciente.isAtivo() || paciente.isExcluido()) {
            throw new ConsultaDadosInvalidosException("O paciente não está apto para agendar consultas.");
        }

        if (usuarioAutenticado.tipo() == TipoUsuario.PACIENTE && !usuarioAutenticado.uuid().equals(paciente.getId())) {
            throw new ConsultaDadosInvalidosException("Um paciente só pode agendar consultas para si mesmo.");
        }
    }

    @Override
    public Consulta cadastrarConsulta(ConsultaCadastroDTO consultaCadastroDTO, UsuarioAutenticado usuarioAutenticado) {
        Agenda agenda = buscarAgendaPorId(consultaCadastroDTO.agendaId());
        Paciente paciente = buscarPacientePorId(consultaCadastroDTO.pacienteId());

        HorarioAgenda horario = agenda.getHorarios().stream()
                .filter(horarioAgenda -> horarioAgenda.getId().equals(consultaCadastroDTO.horarioId()))
                .findFirst().orElseThrow(() -> new ConsultaDadosInvalidosException("Horário não encontrado para a agendamento."));

        validarPaciente(paciente, usuarioAutenticado);
        validarAgendamento(agenda, consultaCadastroDTO, horario);

        Consulta consulta = repository.salvar(new Consulta(UUID.randomUUID(), paciente, agenda, consultaCadastroDTO.data(), horario.getHorario()));

        consultaEventPublisher.publicar(
                new ConsultaEvent(
                        ConsultaEventType.CONSULTA_CRIADA,
                        consulta.getId(),
                        consulta.getPaciente().getId(),
                        consulta.getData().atTime(consulta.getHorario())
                )
        );

        return consulta;
    }

    @Override
    public Consulta cancelarConsulta(UUID consultaId, UsuarioAutenticado usuarioAutenticado) {
        Consulta consulta = buscarPorId(consultaId);
        consulta.cancelar();
        Consulta salvo = repository.salvar(consulta);
        publicarNotificacao(
                new ConsultaEvent(
                        ConsultaEventType.CONSULTA_ATUALIZADA,
                        consulta.getId(),
                        consulta.getPaciente().getId(),
                        consulta.getData().atTime(consulta.getHorario())
                )
        );
        return salvo;
    }

    @Override
    public Consulta realizarConsulta(UUID consultaId, UsuarioAutenticado usuarioAutenticado) {
        Consulta consulta = buscarPorId(consultaId);
        consulta.realizar();
        Consulta salvo = repository.salvar(consulta);
        publicarNotificacao(
                new ConsultaEvent(
                        ConsultaEventType.CONSULTA_ATUALIZADA,
                        consulta.getId(),
                        consulta.getPaciente().getId(),
                        consulta.getData().atTime(consulta.getHorario())
                )
        );
        return salvo;
    }

    @Override
    public Consulta confirmarConsulta(UUID consultaId, UsuarioAutenticado usuarioAutenticado) {
        Consulta consulta = buscarPorId(consultaId);
        consulta.confirmar();
        Consulta salvo = repository.salvar(consulta);
        publicarNotificacao(
                new ConsultaEvent(
                        ConsultaEventType.CONSULTA_ATUALIZADA,
                        consulta.getId(),
                        consulta.getPaciente().getId(),
                        consulta.getData().atTime(consulta.getHorario())
                )
        );
        return salvo;
    }

    @Override
    public Consulta marcarComoAusente(UUID consultaId, UsuarioAutenticado usuarioAutenticado) {
        Consulta consulta = buscarPorId(consultaId);
        consulta.marcarComoAusente();
        Consulta salvo = repository.salvar(consulta);
        publicarNotificacao(
                new ConsultaEvent(
                        ConsultaEventType.CONSULTA_ATUALIZADA,
                        consulta.getId(),
                        consulta.getPaciente().getId(),
                        consulta.getData().atTime(consulta.getHorario())
                )
        );
        return salvo;
    }

    private void validarAgendamento(Agenda agenda, ConsultaCadastroDTO consulta, HorarioAgenda horario) {
        if (consulta.data().isBefore(LocalDate.now())) {
            throw new ConsultaDadosInvalidosException("A data da consulta não pode estar no passado.");
        }

        validarMedico(agenda.getMedico());
        validarHospital(agenda.getHospital());

        if (!agenda.possuiHorario(consulta.data().getDayOfWeek(), horario.getHorario())) {
            throw new ConsultaDadosInvalidosException("Horário não encontrado para a agendamento.");
        }

        if (repository.existeConsulta(agenda.getId(), consulta.data(), horario.getHorario())) {
            throw new ConsultaDadosInvalidosException("Horário não esta disponível para agendamento.");
        }

    }

    private void publicarNotificacao(ConsultaEvent consultaEvent) {
        consultaEventPublisher.publicarAtualizacao(consultaEvent);
    }

    private Consulta buscarPorId(UUID consultaId) {
        return repository.buscarPorId(consultaId).orElseThrow(() -> new ConsultaNaoEncontradaException());
    }

    private Agenda buscarAgendaPorId(UUID agendaId) {
        return agendaRepository.buscarPorId(agendaId).orElseThrow(() -> new ConsultaNaoEncontradaException());
    }

    private Paciente buscarPacientePorId(UUID pacienteId) {
        var exception = new ConsultaNaoEncontradaException("Paciente especificado para a consulta não foi encontrado.");
        Usuario usuario = usuarioRepository.buscarPorId(pacienteId)
                .orElseThrow(() -> exception);

        return switch (usuario) {
            case Paciente paciente -> paciente;
            default -> throw exception;
        };
    }
}
