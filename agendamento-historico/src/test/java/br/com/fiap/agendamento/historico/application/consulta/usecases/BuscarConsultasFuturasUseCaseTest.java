package br.com.fiap.agendamento.historico.application.consulta.usecases;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasFuturasUseCase;
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
class BuscarConsultasFuturasUseCaseTest {

    @Mock
    private ConsultaReadPort consultaReadPort;

    private BuscarConsultasFuturasUseCase useCase;

    private UUID pacienteId;
    private ConsultaHistorico consultaFutura;
    private ConsultaHistorico consultaPassada;
    private ConsultaHistorico consultaHojeFutura;
    private ConsultaHistorico consultaHojePassada;

    @BeforeEach
    void setUp() {
        useCase = new br.com.fiap.agendamento.historico.application.consulta.service.BuscarConsultasFuturasService(consultaReadPort);
        
        pacienteId = UUID.randomUUID();
        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();
        
        consultaFutura = new ConsultaHistorico(
                UUID.randomUUID(),
                pacienteId,
                UUID.randomUUID(),
                hoje.plusDays(1),
                LocalTime.of(10, 0),
                StatusConsulta.AGENDADA,
                LocalDateTime.now()
        );
        
        consultaPassada = new ConsultaHistorico(
                UUID.randomUUID(),
                pacienteId,
                UUID.randomUUID(),
                hoje.minusDays(1),
                LocalTime.of(10, 0),
                StatusConsulta.REALIZADA,
                LocalDateTime.now()
        );
        
        consultaHojeFutura = new ConsultaHistorico(
                UUID.randomUUID(),
                pacienteId,
                UUID.randomUUID(),
                hoje,
                agora.plusHours(1),
                StatusConsulta.CONFIRMADA,
                LocalDateTime.now()
        );
        
        consultaHojePassada = new ConsultaHistorico(
                UUID.randomUUID(),
                pacienteId,
                UUID.randomUUID(),
                hoje,
                agora.minusHours(1),
                StatusConsulta.REALIZADA,
                LocalDateTime.now()
        );
    }

    @Test
    void buscarConsultasFuturas_ComConsultasFuturas_DeveRetornarApenasFuturas() {
        when(consultaReadPort.buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class)))
                .thenReturn(List.of(consultaFutura, consultaHojeFutura));

        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturas(pacienteId, pacienteId, "ADMINISTRADOR");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(consultaReadPort, times(1)).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void buscarConsultasFuturas_SemConsultasFuturas_DeveRetornarListaVazia() {
        when(consultaReadPort.buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class)))
                .thenReturn(List.of());

        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturas(pacienteId, pacienteId, "ADMINISTRADOR");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(consultaReadPort, times(1)).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void buscarConsultasFuturas_ComConsultaMesmoDiaFutura_DeveIncluir() {
        when(consultaReadPort.buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class)))
                .thenReturn(List.of(consultaHojeFutura));

        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturas(pacienteId, pacienteId, "ADMINISTRADOR");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(LocalDate.now(), resultado.get(0).data());
        verify(consultaReadPort, times(1)).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void buscarConsultasFuturas_ComConsultaMesmoDiaPassada_NaoDeveIncluir() {
        when(consultaReadPort.buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class)))
                .thenReturn(List.of());

        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturas(pacienteId, pacienteId, "ADMINISTRADOR");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(consultaReadPort, times(1)).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void buscarConsultasFuturas_PacienteAcessandoPropriasConsultas_DevePermitir() {
        when(consultaReadPort.buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class)))
                .thenReturn(List.of(consultaFutura));

        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturas(pacienteId, pacienteId, "PACIENTE");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(consultaReadPort, times(1)).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void buscarConsultasFuturas_PacienteAcessandoConsultasDeOutroPaciente_DeveNegar() {
        UUID outroPacienteId = UUID.randomUUID();

        assertThrows(SecurityException.class, () -> {
            useCase.buscarConsultasFuturas(outroPacienteId, pacienteId, "PACIENTE");
        });

        verify(consultaReadPort, never()).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void buscarConsultasFuturas_EnfermeiroAcessandoConsultasPaciente_DevePermitir() {
        when(consultaReadPort.buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class)))
                .thenReturn(List.of(consultaFutura));

        UUID enfermeiroId = UUID.randomUUID();
        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturas(pacienteId, enfermeiroId, "ENFERMEIRO");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(consultaReadPort, times(1)).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void buscarConsultasFuturas_AdministradorAcessandoConsultasPaciente_DevePermitir() {
        when(consultaReadPort.buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class)))
                .thenReturn(List.of(consultaFutura));

        UUID adminId = UUID.randomUUID();
        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturas(pacienteId, adminId, "ADMINISTRADOR");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(consultaReadPort, times(1)).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void buscarConsultasFuturas_ComAuthenticatedUserIdNulo_DeveLancarExcecao() {
        assertThrows(NullPointerException.class, () -> {
            useCase.buscarConsultasFuturas(pacienteId, null, "PACIENTE");
        });

        verify(consultaReadPort, never()).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void buscarConsultasFuturas_ComRoleNula_DevePermitirParaAdministrador() {
        when(consultaReadPort.buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class)))
                .thenReturn(List.of(consultaFutura));

        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturas(pacienteId, pacienteId, null);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(consultaReadPort, times(1)).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void buscarConsultasFuturas_ComRoleVazia_DevePermitirParaAdministrador() {
        when(consultaReadPort.buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class)))
                .thenReturn(List.of(consultaFutura));

        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturas(pacienteId, pacienteId, "");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(consultaReadPort, times(1)).buscarConsultasFuturasPorPacienteId(any(UUID.class), any(LocalDate.class), any(LocalTime.class));
    }
}
