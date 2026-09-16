package br.com.fiap.agendamento.historico.application.consulta.usecases;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasFuturasUseCase;
import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.domain.consulta.enums.StatusConsulta;
import br.com.fiap.agendamento.historico.domain.consulta.ports.out.ConsultaHistoricoRepositoryPort;
import br.com.fiap.agendamento.historico.domain.hospital.entity.HospitalHistorico;
import br.com.fiap.agendamento.historico.domain.usuario.entity.MedicoHistorico;
import br.com.fiap.agendamento.historico.domain.usuario.entity.PacienteHistorico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarConsultasFuturasUseCaseTest {

    @Mock
    private ConsultaHistoricoRepositoryPort repositoryPort;

    @InjectMocks
    private BuscarConsultasFuturasUseCaseImpl useCase;

    private UUID pacienteId;
    private ConsultaHistorico consultaFutura;
    private ConsultaHistorico consultaPassada;

    @BeforeEach
    void setUp() {
        pacienteId = UUID.randomUUID();
        
        UUID medicoId = UUID.randomUUID();
        UUID hospitalId = UUID.randomUUID();
        
        PacienteHistorico paciente = new PacienteHistorico(
            pacienteId,
            "João Silva",
            "joao@email.com",
            "11999999999"
        );
        
        MedicoHistorico medico = new MedicoHistorico(
            medicoId,
            "Dr. Carlos",
            "carlos@email.com"
        );
        
        HospitalHistorico hospital = new HospitalHistorico(
            hospitalId,
            "Hospital Central"
        );
        
        LocalDateTime agora = LocalDateTime.now();
        
        consultaFutura = new ConsultaHistorico(
            UUID.randomUUID(),
            paciente,
            medico,
            hospital,
            agora.plusDays(10).toLocalDate(),
            LocalTime.of(14, 0),
            StatusConsulta.AGENDADA,
            agora.plusDays(10)
        );
        
        consultaPassada = new ConsultaHistorico(
            UUID.randomUUID(),
            paciente,
            medico,
            hospital,
            agora.minusDays(5).toLocalDate(),
            LocalTime.of(10, 0),
            StatusConsulta.REALIZADA,
            agora.minusDays(5)
        );
    }

    @Test
    void deveFiltrarERetornarApenasConsultasFuturas() {
        List<ConsultaHistorico> todasConsultas = List.of(consultaFutura, consultaPassada);
        
        when(repositoryPort.buscarConsultasFuturasPorPaciente(pacienteId)).thenReturn(todasConsultas);
        
        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturasPorPaciente(pacienteId);
        
        assertEquals(2, resultado.size());
        verify(repositoryPort, times(1)).buscarConsultasFuturasPorPaciente(pacienteId);
    }

    @Test
    void deveIgnorarConsultasPassadasNaFiltragem() {
        List<ConsultaHistorico> apenasFuturas = List.of(consultaFutura);
        
        when(repositoryPort.buscarConsultasFuturasPorPaciente(pacienteId)).thenReturn(apenasFuturas);
        
        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturasPorPaciente(pacienteId);
        
        assertEquals(1, resultado.size());
        assertEquals(consultaFutura.getId(), resultado.get(0).getId());
        verify(repositoryPort, times(1)).buscarConsultasFuturasPorPaciente(pacienteId);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverConsultasFuturas() {
        when(repositoryPort.buscarConsultasFuturasPorPaciente(pacienteId)).thenReturn(List.of());
        
        List<ConsultaHistorico> resultado = useCase.buscarConsultasFuturasPorPaciente(pacienteId);
        
        assertTrue(resultado.isEmpty());
        verify(repositoryPort, times(1)).buscarConsultasFuturasPorPaciente(pacienteId);
    }
}
