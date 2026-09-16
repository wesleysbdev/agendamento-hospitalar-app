package br.com.fiap.agendamento.historico.application.consulta.ports.in;

import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;

import java.util.Optional;
import java.util.UUID;

public interface BuscarConsultaPorIdUseCase {

    Optional<ConsultaHistorico> buscarPorId(UUID id);
}
