package br.com.fiap.agendamento.gerenciamento.application.agenda.usecases;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.consulta.AgendaDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoConsultaAgenda;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.out.AgendaRepository;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.exception.AgendaNaoEncontradaException;

import java.util.List;
import java.util.UUID;

public class ConsultaAgendaUseCase implements GestaoConsultaAgenda {

    private final AgendaRepository agendaRepository;

    public ConsultaAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

//    @Override
//    public List<AgendaDTO> listarAgendas() {
//        List<Agenda> agendas = agendaRepository.listar();
//        return agendas.stream().map(this::converterParaDTO).toList();
//    }
//
//    @Override
//    public AgendaDTO buscarAgendaPorUuid(UUID uuid) {
//        Agenda agenda = validarAgendaPorUuid(uuid);
//        return converterParaDTO(agenda);
//    }
//
//    @Override
//    public List<AgendaDTO> buscarAgendasPorMedico(UUID medicoUuid) {
//        List<Agenda> agendas = agendaRepository.buscarPorMedico(medicoUuid);
//        return agendas.stream().map(this::converterParaDTO).toList();
//    }
//
//    @Override
//    public List<AgendaDTO> buscarAgendasPorHospital(UUID hospitalUuid) {
//        List<Agenda> agendas = agendaRepository.buscarPorHospital(hospitalUuid);
//        return agendas.stream().map(this::converterParaDTO).toList();
//    }
//
//    private AgendaDTO converterParaDTO(Agenda agenda) {
//        return new AgendaDTO() {
//            @Override
//            public UUID uuid() {
//                return agenda.getUuid();
//            }
//
//            @Override
//            public UUID medicoUuid() {
//                return agenda.getMedico().getUuid();
//            }
//
//            @Override
//            public String medicoNome() {
//                return agenda.getMedico().getNome();
//            }
//
//            @Override
//            public UUID hospitalUuid() {
//                return agenda.getHospital().getUuid();
//            }
//
//            @Override
//            public String hospitalNome() {
//                return agenda.getHospital().getNome();
//            }
//
//            @Override
//            public List<java.time.LocalTime> horarios() {
//                return agenda.getHorarios();
//            }
//        };
//    }
//
//    private Agenda validarAgendaPorUuid(UUID uuid) {
//        return agendaRepository.buscarPorUuid(uuid)
//                .orElseThrow(() -> new AgendaNaoEncontradaException("Agenda não encontrada."));
//    }
}
