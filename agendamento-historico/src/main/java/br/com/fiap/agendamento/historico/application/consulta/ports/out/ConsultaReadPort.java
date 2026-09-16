package br.com.fiap.agendamento.historico.application.consulta.ports.out;

import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface ConsultaReadPort {

    List<ConsultaHistorico> buscarPorPacienteId(UUID pacienteId);

    List<ConsultaHistorico> buscarConsultasFuturasPorPacienteId(UUID pacienteId, LocalDate dataAtual, LocalTime horarioAtual);
}
