package br.com.fiap.agendamento.historico.infrastructure.graphql;

import br.com.fiap.agendamento.historico.application.historico.dto.HistoricoConsultaDTO;
import br.com.fiap.agendamento.historico.application.historico.ports.in.ConsultaHistorico;
import br.com.fiap.agendamento.historico.domain.historico.enums.EstadoConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoricoGraphQLResolverTest {

    @Mock
    private ConsultaHistorico consultaHistorico;

    private HistoricoGraphQLResolver resolver;

    private UUID pacienteUuid;
    private HistoricoConsultaDTO historicoDTO;

    @BeforeEach
    void setUp() {
        resolver = new HistoricoGraphQLResolver(consultaHistorico);
        pacienteUuid = UUID.randomUUID();
        
        historicoDTO = new HistoricoConsultaDTO();
        historicoDTO.setConsultaUuid(UUID.randomUUID());
        historicoDTO.setHorario(LocalDateTime.now().plusDays(1));
        historicoDTO.setEstado(EstadoConsulta.AGENDADA);
    }

    @Test
    void deveRetornarHistoricoPaciente() {
        when(consultaHistorico.buscarHistoricoPaciente(pacienteUuid))
            .thenReturn(List.of(historicoDTO));

        List<HistoricoConsultaDTO> resultado = resolver.historicoPaciente(pacienteUuid);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(historicoDTO.getConsultaUuid(), resultado.get(0).getConsultaUuid());
        
        verify(consultaHistorico).buscarHistoricoPaciente(pacienteUuid);
        verifyNoMoreInteractions(consultaHistorico);
    }

    @Test
    void deveRetornarConsultasFuturas() {
        when(consultaHistorico.buscarConsultasFuturas(pacienteUuid))
            .thenReturn(List.of(historicoDTO));

        List<HistoricoConsultaDTO> resultado = resolver.consultasFuturas(pacienteUuid);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(historicoDTO.getConsultaUuid(), resultado.get(0).getConsultaUuid());
        
        verify(consultaHistorico).buscarConsultasFuturas(pacienteUuid);
        verifyNoMoreInteractions(consultaHistorico);
    }

    @Test
    void devePropagarExcecaoQuandoUseCaseLancarErroEmHistoricoPaciente() {
        when(consultaHistorico.buscarHistoricoPaciente(pacienteUuid))
            .thenThrow(new RuntimeException("Histórico não encontrado"));

        assertThrows(RuntimeException.class, () -> resolver.historicoPaciente(pacienteUuid));
        
        verify(consultaHistorico).buscarHistoricoPaciente(pacienteUuid);
        verifyNoMoreInteractions(consultaHistorico);
    }

    @Test
    void devePropagarExcecaoQuandoUseCaseLancarErroEmConsultasFuturas() {
        when(consultaHistorico.buscarConsultasFuturas(pacienteUuid))
            .thenThrow(new RuntimeException("Não há consultas futuras"));

        assertThrows(RuntimeException.class, () -> resolver.consultasFuturas(pacienteUuid));
        
        verify(consultaHistorico).buscarConsultasFuturas(pacienteUuid);
        verifyNoMoreInteractions(consultaHistorico);
    }

    @Test
    void deveRetornarListaVaziaQuandoUseCaseRetornarVazio() {
        when(consultaHistorico.buscarHistoricoPaciente(pacienteUuid))
            .thenReturn(List.of());

        List<HistoricoConsultaDTO> resultado = resolver.historicoPaciente(pacienteUuid);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        
        verify(consultaHistorico).buscarHistoricoPaciente(pacienteUuid);
        verifyNoMoreInteractions(consultaHistorico);
    }
}
