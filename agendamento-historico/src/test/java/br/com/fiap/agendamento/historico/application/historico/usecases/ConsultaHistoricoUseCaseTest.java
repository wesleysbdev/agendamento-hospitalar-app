package br.com.fiap.agendamento.historico.application.historico.usecases;

import br.com.fiap.agendamento.historico.application.historico.dto.HistoricoConsultaDTO;
import br.com.fiap.agendamento.historico.application.historico.ports.out.HistoricoRepository;
import br.com.fiap.agendamento.historico.domain.historico.entity.HistoricoConsulta;
import br.com.fiap.agendamento.historico.domain.historico.enums.EstadoConsulta;
import br.com.fiap.agendamento.historico.domain.historico.exception.HistoricoNaoEncontradoException;
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
class ConsultaHistoricoUseCaseTest {

    @Mock
    private HistoricoRepository historicoRepository;

    private ConsultaHistoricoUseCase useCase;

    private UUID pacienteUuid;
    private HistoricoConsulta historicoConsulta;

    @BeforeEach
    void setUp() {
        useCase = new ConsultaHistoricoUseCase(historicoRepository);
        pacienteUuid = UUID.randomUUID();
        
        UUID consultaUuid = UUID.randomUUID();
        UUID medicoUuid = UUID.randomUUID();
        UUID hospitalUuid = UUID.randomUUID();
        
        historicoConsulta = new HistoricoConsulta(
            consultaUuid,
            LocalDateTime.now().plusDays(1),
            EstadoConsulta.AGENDADA,
            new HistoricoConsulta.PacienteInfo(pacienteUuid, "João Silva"),
            new HistoricoConsulta.MedicoInfo(medicoUuid, "Dr. Carlos", "CRM12345"),
            new HistoricoConsulta.HospitalInfo(hospitalUuid, "Hospital Central", "Rua A, 123")
        );
    }

    @Test
    void deveRetornarHistoricoQuandoExistiremConsultas() {
        when(historicoRepository.buscarPorPacienteUuid(pacienteUuid))
            .thenReturn(List.of(historicoConsulta));

        List<HistoricoConsultaDTO> resultado = useCase.buscarHistoricoPaciente(pacienteUuid);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pacienteUuid, resultado.get(0).getPaciente().getUuid());
        assertEquals("João Silva", resultado.get(0).getPaciente().getNome());
        assertEquals(EstadoConsulta.AGENDADA, resultado.get(0).getEstado());
        
        verify(historicoRepository).buscarPorPacienteUuid(pacienteUuid);
        verifyNoMoreInteractions(historicoRepository);
    }

    @Test
    void deveLancarExcecaoQuandoHistoricoVazio() {
        when(historicoRepository.buscarPorPacienteUuid(pacienteUuid))
            .thenReturn(List.of());

        HistoricoNaoEncontradoException exception = assertThrows(
            HistoricoNaoEncontradoException.class,
            () -> useCase.buscarHistoricoPaciente(pacienteUuid)
        );

        assertTrue(exception.getMessage().contains("Histórico não encontrado"));
        assertTrue(exception.getMessage().contains(pacienteUuid.toString()));
        
        verify(historicoRepository).buscarPorPacienteUuid(pacienteUuid);
        verifyNoMoreInteractions(historicoRepository);
    }

    @Test
    void deveRetornarConsultasFuturasQuandoExistirem() {
        LocalDateTime agora = LocalDateTime.now();
        HistoricoConsulta consultaFutura = new HistoricoConsulta(
            UUID.randomUUID(),
            agora.plusDays(2),
            EstadoConsulta.CONFIRMADA,
            new HistoricoConsulta.PacienteInfo(pacienteUuid, "Maria Santos"),
            new HistoricoConsulta.MedicoInfo(UUID.randomUUID(), "Dra. Ana", "CRM54321"),
            new HistoricoConsulta.HospitalInfo(UUID.randomUUID(), "Hospital Norte", "Rua B, 456")
        );

        when(historicoRepository.buscarPorPacienteUuidEHorarioPosterior(eq(pacienteUuid), any(LocalDateTime.class)))
            .thenReturn(List.of(consultaFutura));

        List<HistoricoConsultaDTO> resultado = useCase.buscarConsultasFuturas(pacienteUuid);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pacienteUuid, resultado.get(0).getPaciente().getUuid());
        assertEquals(EstadoConsulta.CONFIRMADA, resultado.get(0).getEstado());
        
        verify(historicoRepository).buscarPorPacienteUuidEHorarioPosterior(eq(pacienteUuid), any(LocalDateTime.class));
        verifyNoMoreInteractions(historicoRepository);
    }

    @Test
    void deveLancarExcecaoQuandoNaoHouverConsultasFuturas() {
        when(historicoRepository.buscarPorPacienteUuidEHorarioPosterior(eq(pacienteUuid), any(LocalDateTime.class)))
            .thenReturn(List.of());

        HistoricoNaoEncontradoException exception = assertThrows(
            HistoricoNaoEncontradoException.class,
            () -> useCase.buscarConsultasFuturas(pacienteUuid)
        );

        assertTrue(exception.getMessage().contains("Não há consultas futuras"));
        assertTrue(exception.getMessage().contains(pacienteUuid.toString()));
        
        verify(historicoRepository).buscarPorPacienteUuidEHorarioPosterior(eq(pacienteUuid), any(LocalDateTime.class));
        verifyNoMoreInteractions(historicoRepository);
    }

    @Test
    void deveMapearCorretamenteTodosOsCamposDoDTO() {
        UUID consultaUuid = UUID.randomUUID();
        UUID medicoUuid = UUID.randomUUID();
        UUID hospitalUuid = UUID.randomUUID();
        LocalDateTime horario = LocalDateTime.now().plusHours(5);

        HistoricoConsulta consulta = new HistoricoConsulta(
            consultaUuid,
            horario,
            EstadoConsulta.REALIZADA,
            new HistoricoConsulta.PacienteInfo(pacienteUuid, "Pedro Costa"),
            new HistoricoConsulta.MedicoInfo(medicoUuid, "Dr. Roberto", "CRM98765"),
            new HistoricoConsulta.HospitalInfo(hospitalUuid, "Hospital Sul", "Av. C, 789")
        );

        when(historicoRepository.buscarPorPacienteUuid(pacienteUuid))
            .thenReturn(List.of(consulta));

        List<HistoricoConsultaDTO> resultado = useCase.buscarHistoricoPaciente(pacienteUuid);

        HistoricoConsultaDTO dto = resultado.get(0);
        assertEquals(consultaUuid, dto.getConsultaUuid());
        assertEquals(horario, dto.getHorario());
        assertEquals(EstadoConsulta.REALIZADA, dto.getEstado());
        assertEquals(pacienteUuid, dto.getPaciente().getUuid());
        assertEquals("Pedro Costa", dto.getPaciente().getNome());
        assertEquals(medicoUuid, dto.getMedico().getUuid());
        assertEquals("Dr. Roberto", dto.getMedico().getNome());
        assertEquals("CRM98765", dto.getMedico().getCrm());
        assertEquals(hospitalUuid, dto.getHospital().getUuid());
        assertEquals("Hospital Sul", dto.getHospital().getNome());
        assertEquals("Av. C, 789", dto.getHospital().getEndereco());
    }

    @Test
    void deveRetornarMultiplosHistoricosQuandoExistiremVariasConsultas() {
        HistoricoConsulta consulta1 = new HistoricoConsulta(
            UUID.randomUUID(),
            LocalDateTime.now().minusDays(10),
            EstadoConsulta.REALIZADA,
            new HistoricoConsulta.PacienteInfo(pacienteUuid, "Paciente 1"),
            new HistoricoConsulta.MedicoInfo(UUID.randomUUID(), "Medico 1", "CRM1"),
            new HistoricoConsulta.HospitalInfo(UUID.randomUUID(), "Hospital 1", "End 1")
        );

        HistoricoConsulta consulta2 = new HistoricoConsulta(
            UUID.randomUUID(),
            LocalDateTime.now().minusDays(5),
            EstadoConsulta.CANCELADA,
            new HistoricoConsulta.PacienteInfo(pacienteUuid, "Paciente 1"),
            new HistoricoConsulta.MedicoInfo(UUID.randomUUID(), "Medico 2", "CRM2"),
            new HistoricoConsulta.HospitalInfo(UUID.randomUUID(), "Hospital 2", "End 2")
        );

        when(historicoRepository.buscarPorPacienteUuid(pacienteUuid))
            .thenReturn(List.of(consulta1, consulta2));

        List<HistoricoConsultaDTO> resultado = useCase.buscarHistoricoPaciente(pacienteUuid);

        assertEquals(2, resultado.size());
        assertEquals(EstadoConsulta.REALIZADA, resultado.get(0).getEstado());
        assertEquals(EstadoConsulta.CANCELADA, resultado.get(1).getEstado());
    }
}
