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
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoAutorizadoException;

import java.time.LocalDate;
import java.util.Set;
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

    private static void validarMedico(Medico medico, UsuarioAutenticado usuarioAutenticado) {
        if (!medico.isAtivo() || medico.isExcluido()) {
            throw new ConsultaDadosInvalidosException("Médico não pode realizar atendimentos.");
        }

        if (usuarioAutenticado.tipo() == TipoUsuario.MEDICO && !usuarioAutenticado.uuid().equals(medico.getId())) {
            throw new UsuarioNaoAutorizadoException();
        }
    }

    private static void validarPaciente(Paciente paciente, UsuarioAutenticado usuarioAutenticado) {
        if (!paciente.isAtivo() || paciente.isExcluido()) {
            throw new ConsultaDadosInvalidosException("O paciente não está apto para agendar consultas.");
        }

        if (usuarioAutenticado.tipo() == TipoUsuario.PACIENTE && !usuarioAutenticado.uuid().equals(paciente.getId())) {
            throw new UsuarioNaoAutorizadoException();
        }
    }

    public static void verificaPermissao(TipoUsuario tipoUsuario, Set<TipoUsuario> tiposPermitidos) {
        if (!tiposPermitidos.contains(tipoUsuario)) {
            throw new UsuarioNaoAutorizadoException();
        }
    }

    @Override
    public Consulta cadastrarConsulta(ConsultaCadastroDTO consultaCadastroDTO, UsuarioAutenticado usuarioAutenticado) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO, TipoUsuario.MEDICO, TipoUsuario.PACIENTE));
        Agenda agenda = buscarAgendaPorId(consultaCadastroDTO.agendaId());
        Paciente paciente = buscarPacientePorId(consultaCadastroDTO.pacienteId());

        HorarioAgenda horario = agenda.getHorarios().stream()
                .filter(horarioAgenda -> horarioAgenda.getId().equals(consultaCadastroDTO.horarioId()))
                .findFirst().orElseThrow(() -> new ConsultaDadosInvalidosException("Horário não encontrado para a agendamento."));

        validarPaciente(paciente, usuarioAutenticado);
        validarAgendamento(agenda, consultaCadastroDTO, horario, usuarioAutenticado);

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
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO, TipoUsuario.MEDICO, TipoUsuario.PACIENTE));
        Consulta consulta = buscarPorId(consultaId);

        validarPaciente(consulta.getPaciente(), usuarioAutenticado);
        validarMedico(consulta.getAgenda().getMedico(), usuarioAutenticado);

        consulta.cancelar();
        return salvarConsultaAtualizada(consulta);
    }

    @Override
    public Consulta realizarConsulta(UUID consultaId, UsuarioAutenticado usuarioAutenticado) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.MEDICO));
        Consulta consulta = buscarPorId(consultaId);

        validarMedico(consulta.getAgenda().getMedico(), usuarioAutenticado);

        consulta.realizar();
        return salvarConsultaAtualizada(consulta);
    }

    @Override
    public Consulta confirmarConsulta(UUID consultaId, UsuarioAutenticado usuarioAutenticado) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO, TipoUsuario.PACIENTE));
        Consulta consulta = buscarPorId(consultaId);

        validarPaciente(consulta.getPaciente(), usuarioAutenticado);

        consulta.confirmar();
        Consulta salvo = repository.salvar(consulta);
        return salvarConsultaAtualizada(consulta);
    }

    @Override
    public Consulta marcarComoAusente(UUID consultaId, UsuarioAutenticado usuarioAutenticado) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO, TipoUsuario.MEDICO));
        Consulta consulta = buscarPorId(consultaId);

        validarMedico(consulta.getAgenda().getMedico(), usuarioAutenticado);

        consulta.marcarComoAusente();
        return salvarConsultaAtualizada(consulta);
    }

    private void validarAgendamento(Agenda agenda, ConsultaCadastroDTO consulta, HorarioAgenda horario, UsuarioAutenticado usuarioAutenticado) {
        if (consulta.data().isBefore(LocalDate.now())) {
            throw new ConsultaDadosInvalidosException("A data da consulta não pode estar no passado.");
        }

        validarMedico(agenda.getMedico(), usuarioAutenticado);
        validarHospital(agenda.getHospital());

        if (!agenda.possuiHorario(consulta.data().getDayOfWeek(), horario.getHorario())) {
            throw new ConsultaDadosInvalidosException("Horário não encontrado para a agendamento.");
        }

        if (repository.existeConsulta(agenda.getId(), consulta.data(), horario.getHorario())) {
            throw new ConsultaDadosInvalidosException("Horário não esta disponível para agendamento.");
        }

    }

    private Consulta salvarConsultaAtualizada(Consulta consulta) {
        Consulta salvo = repository.salvar(consulta);

        consultaEventPublisher.publicarAtualizacao(
                new ConsultaEvent(
                        ConsultaEventType.CONSULTA_ATUALIZADA,
                        consulta.getId(),
                        consulta.getPaciente().getId(),
                        consulta.getData().atTime(consulta.getHorario())
                )
        );

        return salvo;
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
