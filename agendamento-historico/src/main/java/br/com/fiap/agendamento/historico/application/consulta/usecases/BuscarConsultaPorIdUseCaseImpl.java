package br.com.fiap.agendamento.historico.application.consulta.usecases;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultaPorIdUseCase;
import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.domain.consulta.ports.out.ConsultaHistoricoRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class BuscarConsultaPorIdUseCaseImpl implements BuscarConsultaPorIdUseCase {

    private final ConsultaHistoricoRepositoryPort repositoryPort;

    @Override
    public Optional<ConsultaHistorico> buscarPorId(UUID id) {
        return repositoryPort.buscarPorId(id);
    }
}
