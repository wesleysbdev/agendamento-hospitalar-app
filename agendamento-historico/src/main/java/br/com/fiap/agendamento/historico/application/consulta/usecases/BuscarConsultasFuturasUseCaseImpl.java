package br.com.fiap.agendamento.historico.application.consulta.usecases;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasFuturasUseCase;
import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.domain.consulta.ports.out.ConsultaHistoricoRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class BuscarConsultasFuturasUseCaseImpl implements BuscarConsultasFuturasUseCase {

    private final ConsultaHistoricoRepositoryPort repositoryPort;

    @Override
    public List<ConsultaHistorico> buscarConsultasFuturasPorPaciente(UUID pacienteId) {
        return repositoryPort.buscarConsultasFuturasPorPaciente(pacienteId);
    }
}
