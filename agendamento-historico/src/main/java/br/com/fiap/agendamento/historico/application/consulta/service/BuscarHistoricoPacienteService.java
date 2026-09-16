package br.com.fiap.agendamento.historico.application.consulta.service;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarHistoricoPacienteUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.out.ConsultaReadPort;
import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BuscarHistoricoPacienteService implements BuscarHistoricoPacienteUseCase {

    private final ConsultaReadPort consultaReadPort;

    @Override
    public List<ConsultaHistorico> buscarHistoricoPaciente(UUID pacienteId, UUID authenticatedUserId, String role) {
        if ("PACIENTE".equals(role) && !authenticatedUserId.equals(pacienteId)) {
            throw new SecurityException("Paciente só pode visualizar seu próprio histórico");
        }
        return consultaReadPort.buscarPorPacienteId(pacienteId);
    }
}
