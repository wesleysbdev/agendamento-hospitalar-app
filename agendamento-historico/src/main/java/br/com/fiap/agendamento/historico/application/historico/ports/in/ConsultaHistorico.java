package br.com.fiap.agendamento.historico.application.historico.ports.in;

import br.com.fiap.agendamento.historico.application.historico.dto.HistoricoConsultaDTO;

import java.util.List;
import java.util.UUID;

public interface ConsultaHistorico {

    List<HistoricoConsultaDTO> buscarHistoricoPaciente(UUID pacienteUuid);

    List<HistoricoConsultaDTO> buscarConsultasFuturas(UUID pacienteUuid);
}
