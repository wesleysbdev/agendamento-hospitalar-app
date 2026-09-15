package br.com.fiap.agendamento.gerenciamento.application.agenda.usecases;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.AgendaDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.HorarioAgendaDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoConsultaAgenda;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.out.AgendaRepository;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.HorarioAgenda;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.exception.AgendaNaoEncontradaException;

import java.util.List;
import java.util.UUID;

public class ConsultaAgendaUseCase implements GestaoConsultaAgenda {

    private final AgendaRepository agendaRepository;

    public ConsultaAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Override
    public List<AgendaDTO> listarAgendas(UsuarioAutenticado usuarioAutenticado) {
        List<Agenda> agendas = agendaRepository.listar();
        return agendas.stream().map(this::converterParaDTO).toList();
    }

    @Override
    public AgendaDTO buscarAgendaPorUuid(UUID uuid, UsuarioAutenticado usuarioAutenticado) {
        Agenda agenda = buscarPorUuid(uuid);
        return converterParaDTO(agenda);
    }

    @Override
    public List<AgendaDTO> buscarAgendasPorMedico(UUID medicoUuid, UsuarioAutenticado usuarioAutenticado) {
        List<Agenda> agendas = agendaRepository.buscarPorMedico(medicoUuid);
        return agendas.stream().map(this::converterParaDTO).toList();
    }

    @Override
    public List<AgendaDTO> buscarAgendasPorHospital(UUID hospitalUuid, UsuarioAutenticado usuarioAutenticado) {
        List<Agenda> agendas = agendaRepository.buscarPorHospital(hospitalUuid);
        return agendas.stream().map(this::converterParaDTO).toList();
    }

    private AgendaDTO converterParaDTO(Agenda agenda) {
        return new AgendaDTO(
                agenda.getUuid(),
                agenda.getMedico().getUuid(),
                agenda.getMedico().getNome(),
                agenda.getHospital().getUuid(),
                agenda.getHospital().getNome(),
                converterHorarioAgendaParaDTO(agenda.getHorarios())
        );
    }

    private List<HorarioAgendaDTO> converterHorarioAgendaParaDTO(List<HorarioAgenda> horarios) {
        return horarios.stream().map(
                horarioAgenda -> new HorarioAgendaDTO(
                        horarioAgenda.getUuid(),
                        horarioAgenda.getDiaSemana(),
                        horarioAgenda.getHorario()
                )
        ).toList();
    }

    private Agenda buscarPorUuid(UUID uuid) {
        return agendaRepository.buscarPorUuid(uuid)
                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada."));
    }
}
