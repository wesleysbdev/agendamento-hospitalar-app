package br.com.fiap.agendamento.historico.application.consulta.usecases;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarHistoricoPacienteUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.out.ConsultaReadPort;
import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.domain.consulta.enums.StatusConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarHistoricoPacienteUseCaseTest {

    @Mock
    private ConsultaReadPort consultaReadPort;

    private BuscarHistoricoPacienteUseCase useCase;

    private UUID pacienteId;
    private ConsultaHistorico consultaHistorico;

    @BeforeEach
    void setUp() {
        useCase = new br.com.fiap.agendamento.historico.application.consulta.service.BuscarHistoricoPacienteService(consultaReadPort);
        
        pacienteId = UUID.randomUUID();
        
        consultaHistorico = new ConsultaHistorico(
                UUID.randomUUID(),
                pacienteId,
                UUID.randomUUID(),
                LocalDate.now(),
                LocalTime.of(10, 0),
                StatusConsulta.AGENDADA,
                LocalDateTime.now()
        );
    }

    @Test
    void buscarHistoricoPaciente_ComConsultas_DeveRetornarLista() {
        when(consultaReadPort.buscarPorPacienteId(pacienteId))
                .thenReturn(List.of(consultaHistorico));

        List<ConsultaHistorico> resultado = useCase.buscarHistoricoPaciente(pacienteId, pacienteId, "ADMINISTRADOR");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pacienteId, resultado.get(0).pacienteId());
        verify(consultaReadPort, times(1)).buscarPorPacienteId(pacienteId);
    }

    @Test
    void buscarHistoricoPaciente_SemConsultas_DeveRetornarListaVazia() {
        when(consultaReadPort.buscarPorPacienteId(pacienteId))
                .thenReturn(List.of());

        List<ConsultaHistorico> resultado = useCase.buscarHistoricoPaciente(pacienteId, pacienteId, "ADMINISTRADOR");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(consultaReadPort, times(1)).buscarPorPacienteId(pacienteId);
    }

    @Test
    void buscarHistoricoPaciente_ComMultiplasConsultas_DeveRetornarTodas() {
        ConsultaHistorico consulta2 = new ConsultaHistorico(
                UUID.randomUUID(),
                pacienteId,
                UUID.randomUUID(),
                LocalDate.now().plusDays(1),
                LocalTime.of(14, 0),
                StatusConsulta.CONFIRMADA,
                LocalDateTime.now()
        );

        when(consultaReadPort.buscarPorPacienteId(pacienteId))
                .thenReturn(List.of(consultaHistorico, consulta2));

        List<ConsultaHistorico> resultado = useCase.buscarHistoricoPaciente(pacienteId, pacienteId, "ADMINISTRADOR");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(consultaReadPort, times(1)).buscarPorPacienteId(pacienteId);
    }

    @Test
    void buscarHistoricoPaciente_PacienteAcessandoProprioHistorico_DevePermitir() {
        when(consultaReadPort.buscarPorPacienteId(pacienteId))
                .thenReturn(List.of(consultaHistorico));

        List<ConsultaHistorico> resultado = useCase.buscarHistoricoPaciente(pacienteId, pacienteId, "PACIENTE");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(consultaReadPort, times(1)).buscarPorPacienteId(pacienteId);
    }

    @Test
    void buscarHistoricoPaciente_PacienteAcessandoHistoricoDeOutroPaciente_DeveNegar() {
        UUID outroPacienteId = UUID.randomUUID();

        assertThrows(SecurityException.class, () -> {
            useCase.buscarHistoricoPaciente(outroPacienteId, pacienteId, "PACIENTE");
        });

        verify(consultaReadPort, never()).buscarPorPacienteId(any());
    }

    @Test
    void buscarHistoricoPaciente_EnfermeiroAcessandoHistoricoPaciente_DevePermitir() {
        when(consultaReadPort.buscarPorPacienteId(pacienteId))
                .thenReturn(List.of(consultaHistorico));

        UUID enfermeiroId = UUID.randomUUID();
        List<ConsultaHistorico> resultado = useCase.buscarHistoricoPaciente(pacienteId, enfermeiroId, "ENFERMEIRO");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(consultaReadPort, times(1)).buscarPorPacienteId(pacienteId);
    }

    @Test
    void buscarHistoricoPaciente_AdministradorAcessandoHistoricoPaciente_DevePermitir() {
        when(consultaReadPort.buscarPorPacienteId(pacienteId))
                .thenReturn(List.of(consultaHistorico));

        UUID adminId = UUID.randomUUID();
        List<ConsultaHistorico> resultado = useCase.buscarHistoricoPaciente(pacienteId, adminId, "ADMINISTRADOR");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(consultaReadPort, times(1)).buscarPorPacienteId(pacienteId);
    }

    @Test
    void buscarHistoricoPaciente_ComAuthenticatedUserIdNulo_DeveLancarExcecao() {
        assertThrows(NullPointerException.class, () -> {
            useCase.buscarHistoricoPaciente(pacienteId, null, "PACIENTE");
        });

        verify(consultaReadPort, never()).buscarPorPacienteId(any());
    }

    @Test
    void buscarHistoricoPaciente_ComRoleNula_DevePermitirParaAdministrador() {
        when(consultaReadPort.buscarPorPacienteId(pacienteId))
                .thenReturn(List.of(consultaHistorico));

        List<ConsultaHistorico> resultado = useCase.buscarHistoricoPaciente(pacienteId, pacienteId, null);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(consultaReadPort, times(1)).buscarPorPacienteId(pacienteId);
    }

    @Test
    void buscarHistoricoPaciente_ComRoleVazia_DevePermitirParaAdministrador() {
        when(consultaReadPort.buscarPorPacienteId(pacienteId))
                .thenReturn(List.of(consultaHistorico));

        List<ConsultaHistorico> resultado = useCase.buscarHistoricoPaciente(pacienteId, pacienteId, "");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(consultaReadPort, times(1)).buscarPorPacienteId(pacienteId);
    }
}
