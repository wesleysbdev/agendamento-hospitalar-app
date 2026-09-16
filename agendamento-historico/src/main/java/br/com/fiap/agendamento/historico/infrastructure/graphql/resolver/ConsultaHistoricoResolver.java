package br.com.fiap.agendamento.historico.infrastructure.graphql.resolver;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultaPorIdUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasFuturasUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasPassadasUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarHistoricoPacienteUseCase;
import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.domain.consulta.exception.AcessoNaoAutorizadoException;
import br.com.fiap.agendamento.historico.infrastructure.graphql.dto.ConsultaGraphQLDTO;
import br.com.fiap.agendamento.historico.infrastructure.graphql.mapper.ConsultaGraphQLMapper;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ConsultaHistoricoResolver {

    private final BuscarHistoricoPacienteUseCase buscarHistoricoPacienteUseCase;
    private final BuscarConsultasFuturasUseCase buscarConsultasFuturasUseCase;
    private final BuscarConsultasPassadasUseCase buscarConsultasPassadasUseCase;
    private final BuscarConsultaPorIdUseCase buscarConsultaPorIdUseCase;
    private final ConsultaGraphQLMapper mapper;

    private void verificarAutorizacaoPaciente(Authentication authentication, UUID pacienteIdSolicitado) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PACIENTE"))) {
            UUID usuarioId = UUID.fromString(authentication.getName());
            if (!usuarioId.equals(pacienteIdSolicitado)) {
                throw new AcessoNaoAutorizadoException("Paciente só pode consultar suas próprias informações");
            }
        }
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public List<ConsultaGraphQLDTO> historicoPorPaciente(@Argument String pacienteId, Authentication authentication) {
        UUID id = UUID.fromString(pacienteId);
        verificarAutorizacaoPaciente(authentication, id);
        List<ConsultaHistorico> consultas = buscarHistoricoPacienteUseCase.buscarHistoricoPorPaciente(id);
        return consultas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public List<ConsultaGraphQLDTO> consultasFuturasPorPaciente(@Argument String pacienteId, Authentication authentication) {
        UUID id = UUID.fromString(pacienteId);
        verificarAutorizacaoPaciente(authentication, id);
        List<ConsultaHistorico> consultas = buscarConsultasFuturasUseCase.buscarConsultasFuturasPorPaciente(id);
        return consultas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public List<ConsultaGraphQLDTO> consultasPassadasPorPaciente(@Argument String pacienteId, Authentication authentication) {
        UUID id = UUID.fromString(pacienteId);
        verificarAutorizacaoPaciente(authentication, id);
        List<ConsultaHistorico> consultas = buscarConsultasPassadasUseCase.buscarConsultasPassadasPorPaciente(id);
        return consultas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public ConsultaGraphQLDTO consultaPorId(@Argument String id, Authentication authentication) {
        UUID consultaId = UUID.fromString(id);
        ConsultaHistorico consulta = buscarConsultaPorIdUseCase.buscarPorId(consultaId)
                .orElse(null);
        
        if (consulta != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PACIENTE"))) {
            UUID usuarioId = UUID.fromString(authentication.getName());
            if (!usuarioId.equals(consulta.getPaciente().getId())) {
                throw new AcessoNaoAutorizadoException("Paciente só pode consultar suas próprias consultas");
            }
        }
        
        return consulta != null ? mapper.toDTO(consulta) : null;
    }
}
