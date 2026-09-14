package br.com.fiap.agendamento.gerenciamento.application.agenda.usecases;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.AgendaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.HorarioAgendaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoCadastroAgenda;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.out.AgendaRepository;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.out.HospitalRepository;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.HorarioAgenda;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.exception.AgendaDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.exception.AgendaNaoEncontradaException;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.exception.HorarioAgendaNaoEncontradoException;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.exception.HospitalNaoEncontradoException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Medico;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoEncontradoException;

import java.util.List;
import java.util.UUID;

public class CadastroAgendaUseCase implements GestaoCadastroAgenda {

    private final AgendaRepository agendaRepository;
    private final UsuarioRepository usuarioRepository;
    private final HospitalRepository hospitalRepository;

    public CadastroAgendaUseCase(
            AgendaRepository agendaRepository,
            UsuarioRepository usuarioRepository,
            HospitalRepository hospitalRepository
    ) {
        this.agendaRepository = agendaRepository;
        this.usuarioRepository = usuarioRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public Agenda cadastrar(AgendaCadastroDTO agendaCadastro, UsuarioAutenticado usuarioAutenticado) {
        Medico medico = buscarMedico(usuarioAutenticado.uuid());

        if (!medico.isAtivo() || medico.isExcluido()) {
            throw new AgendaDadosInvalidosException("O médico precisa estar ativo para manipular agendas.");
        }

        Hospital hospital = buscarHospital(agendaCadastro.hospitalUuid());

        if (!hospital.isAtivo() || hospital.isExcluido()) {
            throw new AgendaDadosInvalidosException("O hospital precisa estar ativo para receber agendas.");
        }

        validarAgendaExistente(medico.getUuid(), hospital.getUuid());

        Agenda agenda = new Agenda(
                UUID.randomUUID(),
                medico,
                hospital,
                criarHorarios(agendaCadastro.horarios())
        );

        return agendaRepository.salvar(agenda);
    }

    @Override
    public Agenda adicionarHorarios(UUID agendaUuid, List<HorarioAgendaCadastroDTO> horarios, UsuarioAutenticado usuarioAutenticado) {
        Agenda agenda = buscarAgendaPorUuid(agendaUuid);
        List<HorarioAgenda> novosHorarios = horarios.stream().map(this::criarHorario).toList();
        agenda.adicionarHorarios(novosHorarios);
        return agendaRepository.salvar(agenda);
    }

    @Override
    public Agenda removerHorario(UUID agendaUuid, UUID horarioUuid, UsuarioAutenticado usuarioAutenticado) {
        Agenda agenda = buscarAgendaPorUuid(agendaUuid);
        HorarioAgenda horarioAgenda = buscarHorarioNaAgenda(agenda.getHorarios(), horarioUuid);
        agenda.removerHorario(horarioAgenda);
        return agendaRepository.salvar(agenda);
    }

    private Medico buscarMedico(UUID uuid) {
        return usuarioRepository.buscarPorUuid(uuid)
                .filter(usuario -> usuario instanceof Medico)
                .map(usuario -> (Medico) usuario)
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Médico não encontrado."));
    }

    private Hospital buscarHospital(UUID uuid) {
        return hospitalRepository.buscarPorUuid(uuid)
                .orElseThrow(() ->
                        new HospitalNaoEncontradoException("Hospital não encontrado."));
    }

    private void validarAgendaExistente(UUID medicoUuid, UUID hospitalUuid) {
        agendaRepository.buscarPorMedicoEHospital(medicoUuid, hospitalUuid)
                .ifPresent(agenda -> {
                    throw new AgendaDadosInvalidosException("O médico já possui uma agenda neste hospital.");
                });
    }

    private Agenda buscarAgendaPorUuid(UUID uuid) {
        return agendaRepository.buscarPorUuid(uuid)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada."));
    }

    private HorarioAgenda criarHorario(HorarioAgendaCadastroDTO horarioAgendaCadastro) {
        return new HorarioAgenda(
                UUID.randomUUID(),
                horarioAgendaCadastro.diaSemana(),
                horarioAgendaCadastro.horario()
        );
    }

    private List<HorarioAgenda> criarHorarios(List<HorarioAgendaCadastroDTO> horarios) {
        return horarios.stream().map(this::criarHorario).toList();
    }

    private HorarioAgenda buscarHorarioNaAgenda(List<HorarioAgenda> horariosDaAgenda, UUID idBuscado) {
        return horariosDaAgenda.stream()
                .filter(obj -> idBuscado.equals(obj.getUuid()))
                .findFirst().orElseThrow(() -> new HorarioAgendaNaoEncontradoException());
    }
}
