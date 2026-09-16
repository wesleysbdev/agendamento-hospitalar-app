package br.com.fiap.agendamento.historico.application.consulta.usecases;

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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarHistoricoPacienteUseCaseTest {

    @Mock
    private ConsultaHistoricoRepositoryPort repositoryPort;

    @InjectMocks
    private BuscarHistoricoPacienteUseCaseImpl useCase;

    private UUID pacienteId;
    private ConsultaHistorico consulta;

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
        
        consulta = new ConsultaHistorico(
            UUID.randomUUID(),
            paciente,
            medico,
            hospital,
            LocalDate.of(2024, 1, 15),
            LocalTime.of(10, 0),
            StatusConsulta.REALIZADA,
            LocalDateTime.of(2024, 1, 10, 9, 0)
        );
    }

    @Test
    void deveRetornarListaCompletaDeConsultasQuandoPacienteIdInformado() {
        List<ConsultaHistorico> consultasEsperadas = List.of(consulta);
        
        when(repositoryPort.buscarPorPacienteId(pacienteId)).thenReturn(consultasEsperadas);
        
        List<ConsultaHistorico> resultado = useCase.buscarHistoricoPorPaciente(pacienteId);
        
        assertEquals(1, resultado.size());
        assertEquals(consulta.getId(), resultado.get(0).getId());
        assertEquals(consulta.getPaciente().getId(), resultado.get(0).getPaciente().getId());
        verify(repositoryPort, times(1)).buscarPorPacienteId(pacienteId);
    }

    @Test
    void deveRetornarListaVaziaQuandoPacienteNaoPossuirHistorico() {
        when(repositoryPort.buscarPorPacienteId(pacienteId)).thenReturn(Collections.emptyList());
        
        List<ConsultaHistorico> resultado = useCase.buscarHistoricoPorPaciente(pacienteId);
        
        assertTrue(resultado.isEmpty());
        verify(repositoryPort, times(1)).buscarPorPacienteId(pacienteId);
    }
}
