package br.com.fiap.agendamento.historico.application.consulta.ports.in;

import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;

import java.util.List;
import java.util.UUID;

public interface BuscarConsultasFuturasUseCase {

    List<ConsultaHistorico> buscarConsultasFuturas(UUID pacienteId, UUID authenticatedUserId, String role);
}
