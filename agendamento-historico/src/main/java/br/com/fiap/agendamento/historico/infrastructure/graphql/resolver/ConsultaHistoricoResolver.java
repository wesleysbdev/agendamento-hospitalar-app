package br.com.fiap.agendamento.historico.infrastructure.graphql.resolver;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasFuturasUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarHistoricoPacienteUseCase;
import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class ConsultaHistoricoResolver {

    private final BuscarHistoricoPacienteUseCase buscarHistoricoPacienteUseCase;
    private final BuscarConsultasFuturasUseCase buscarConsultasFuturasUseCase;

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_ENFERMEIRO', 'ROLE_PACIENTE')")
    public List<ConsultaHistorico> historicoPaciente(
            @Argument("pacienteId") UUID pacienteId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String role = jwt.getClaimAsString("role");
        UUID authenticatedUserId = UUID.fromString(jwt.getSubject());

        return buscarHistoricoPacienteUseCase.buscarHistoricoPaciente(pacienteId, authenticatedUserId, role);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_ENFERMEIRO', 'ROLE_PACIENTE')")
    public List<ConsultaHistorico> consultasFuturas(
            @Argument("pacienteId") UUID pacienteId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String role = jwt.getClaimAsString("role");
        UUID authenticatedUserId = UUID.fromString(jwt.getSubject());

        return buscarConsultasFuturasUseCase.buscarConsultasFuturas(pacienteId, authenticatedUserId, role);
    }
}
