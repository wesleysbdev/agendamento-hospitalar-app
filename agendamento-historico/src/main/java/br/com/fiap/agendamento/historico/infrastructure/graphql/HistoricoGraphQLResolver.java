package br.com.fiap.agendamento.historico.infrastructure.graphql;

import br.com.fiap.agendamento.historico.application.historico.dto.HistoricoConsultaDTO;
import br.com.fiap.agendamento.historico.application.historico.ports.in.ConsultaHistorico;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class HistoricoGraphQLResolver {

    private final ConsultaHistorico consultaHistorico;

    @QueryMapping
    @PreAuthorize("hasAuthority('PACIENTE') or hasAuthority('MEDICO') or hasAuthority('ENFERMEIRO') or hasAuthority('ADMINISTRADOR')")
    public List<HistoricoConsultaDTO> historicoPaciente(@Argument UUID pacienteUuid) {
        return consultaHistorico.buscarHistoricoPaciente(pacienteUuid);
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('PACIENTE') or hasAuthority('MEDICO') or hasAuthority('ENFERMEIRO') or hasAuthority('ADMINISTRADOR')")
    public List<HistoricoConsultaDTO> consultasFuturas(@Argument UUID pacienteUuid) {
        return consultaHistorico.buscarConsultasFuturas(pacienteUuid);
    }
}
