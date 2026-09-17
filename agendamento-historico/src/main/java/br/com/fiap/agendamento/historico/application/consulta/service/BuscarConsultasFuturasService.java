package br.com.fiap.agendamento.historico.application.consulta.service;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasFuturasUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.out.ConsultaReadPort;
import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BuscarConsultasFuturasService implements BuscarConsultasFuturasUseCase {

    private final ConsultaReadPort consultaReadPort;

    @Override
    public List<ConsultaHistorico> buscarConsultasFuturas(UUID pacienteId, UUID authenticatedUserId, String role) {
        if ("PACIENTE".equals(role) && !authenticatedUserId.equals(pacienteId)) {
            throw new SecurityException("Paciente só pode visualizar suas próprias consultas futuras");
        }
        LocalDate dataAtual = LocalDate.now();
        LocalTime horarioAtual = LocalTime.now();
        return consultaReadPort.buscarConsultasFuturasPorPacienteId(pacienteId, dataAtual, horarioAtual);
    }
}
