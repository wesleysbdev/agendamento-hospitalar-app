package br.com.fiap.agendamento.historico.application.historico.ports.out;

import br.com.fiap.agendamento.historico.domain.historico.entity.HistoricoConsulta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface HistoricoRepository {

    List<HistoricoConsulta> buscarPorPacienteUuid(UUID pacienteUuid);

    List<HistoricoConsulta> buscarPorPacienteUuidEHorarioPosterior(UUID pacienteUuid, LocalDateTime horario);
}
