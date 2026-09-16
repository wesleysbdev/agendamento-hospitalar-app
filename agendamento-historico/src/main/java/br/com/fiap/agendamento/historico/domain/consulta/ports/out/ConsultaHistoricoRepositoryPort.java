package br.com.fiap.agendamento.historico.domain.consulta.ports.out;

import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultaHistoricoRepositoryPort {

    Optional<ConsultaHistorico> buscarPorId(UUID id);

    List<ConsultaHistorico> buscarPorPacienteId(UUID pacienteId);

    List<ConsultaHistorico> buscarConsultasFuturasPorPaciente(UUID pacienteId);

    List<ConsultaHistorico> buscarConsultasPassadasPorPaciente(UUID pacienteId);
}
