package br.com.fiap.agendamento.historico.infrastructure.graphql.resolver;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultaPorIdUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasFuturasUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasPassadasUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarHistoricoPacienteUseCase;
import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.infrastructure.graphql.dto.ConsultaGraphQLDTO;
import br.com.fiap.agendamento.historico.infrastructure.graphql.mapper.ConsultaGraphQLMapper;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @QueryMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public List<ConsultaGraphQLDTO> historicoPorPaciente(@Argument String pacienteId) {
        UUID id = UUID.fromString(pacienteId);
        List<ConsultaHistorico> consultas = buscarHistoricoPacienteUseCase.buscarHistoricoPorPaciente(id);
        return consultas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public List<ConsultaGraphQLDTO> consultasFuturasPorPaciente(@Argument String pacienteId) {
        UUID id = UUID.fromString(pacienteId);
        List<ConsultaHistorico> consultas = buscarConsultasFuturasUseCase.buscarConsultasFuturasPorPaciente(id);
        return consultas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public List<ConsultaGraphQLDTO> consultasPassadasPorPaciente(@Argument String pacienteId) {
        UUID id = UUID.fromString(pacienteId);
        List<ConsultaHistorico> consultas = buscarConsultasPassadasUseCase.buscarConsultasPassadasPorPaciente(id);
        return consultas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO', 'PACIENTE')")
    public ConsultaGraphQLDTO consultaPorId(@Argument String id) {
        UUID consultaId = UUID.fromString(id);
        return buscarConsultaPorIdUseCase.buscarPorId(consultaId)
                .map(mapper::toDTO)
                .orElse(null);
    }
}
