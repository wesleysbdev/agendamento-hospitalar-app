package br.com.fiap.agendamento.historico.infrastructure.persistence.adapter;

import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.domain.consulta.enums.StatusConsulta;
import br.com.fiap.agendamento.historico.infrastructure.persistence.mapper.ConsultaHistoricoMapper;
import br.com.fiap.agendamento.historico.infrastructure.persistence.model.ConsultaReadModel;
import br.com.fiap.agendamento.historico.infrastructure.persistence.repository.ConsultaReadRepository;
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
class ConsultaReadRepositoryAdapterTest {

    @Mock
    private ConsultaReadRepository repository;

    @Mock
    private ConsultaHistoricoMapper mapper;

    private ConsultaReadRepositoryAdapter adapter;

    private UUID pacienteId;
    private ConsultaReadModel consultaModel;
    private ConsultaHistorico consultaHistorico;

    @BeforeEach
    void setUp() {
        adapter = new ConsultaReadRepositoryAdapter(repository, mapper);
        
        pacienteId = UUID.randomUUID();
        
        consultaModel = new ConsultaReadModel();
        consultaModel.setId(UUID.randomUUID());
        consultaModel.setPacienteId(pacienteId);
        consultaModel.setAgendaId(UUID.randomUUID());
        consultaModel.setData(LocalDate.now());
        consultaModel.setHorario(LocalTime.of(10, 0));
        consultaModel.setStatus(StatusConsulta.AGENDADA);
        consultaModel.setCriadoEm(LocalDateTime.now());
        
        consultaHistorico = new ConsultaHistorico(
                consultaModel.getId(),
                pacienteId,
                consultaModel.getAgendaId(),
                LocalDate.now(),
                LocalTime.of(10, 0),
                StatusConsulta.AGENDADA,
                LocalDateTime.now()
        );
    }

    @Test
    void buscarPorPacienteId_DeveRetornarHistorico() {
        when(repository.findByPacienteId(pacienteId))
                .thenReturn(List.of(consultaModel));
        when(mapper.paraHistorico(consultaModel))
                .thenReturn(consultaHistorico);

        List<ConsultaHistorico> resultado = adapter.buscarPorPacienteId(pacienteId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pacienteId, resultado.get(0).pacienteId());
        verify(repository, times(1)).findByPacienteId(pacienteId);
        verify(mapper, times(1)).paraHistorico(consultaModel);
    }

    @Test
    void buscarConsultasFuturasPorPacienteId_DeveFiltrarFuturas() {
        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();
        
        ConsultaReadModel consultaFutura = new ConsultaReadModel();
        consultaFutura.setId(UUID.randomUUID());
        consultaFutura.setPacienteId(pacienteId);
        consultaFutura.setAgendaId(UUID.randomUUID());
        consultaFutura.setData(hoje.plusDays(1));
        consultaFutura.setHorario(LocalTime.of(10, 0));
        consultaFutura.setStatus(StatusConsulta.AGENDADA);
        consultaFutura.setCriadoEm(LocalDateTime.now());
        
        ConsultaReadModel consultaPassada = new ConsultaReadModel();
        consultaPassada.setId(UUID.randomUUID());
        consultaPassada.setPacienteId(pacienteId);
        consultaPassada.setAgendaId(UUID.randomUUID());
        consultaPassada.setData(hoje.minusDays(1));
        consultaPassada.setHorario(LocalTime.of(10, 0));
        consultaPassada.setStatus(StatusConsulta.REALIZADA);
        consultaPassada.setCriadoEm(LocalDateTime.now());
        
        ConsultaHistorico historicoFuturo = new ConsultaHistorico(
                consultaFutura.getId(),
                pacienteId,
                consultaFutura.getAgendaId(),
                consultaFutura.getData(),
                consultaFutura.getHorario(),
                consultaFutura.getStatus(),
                consultaFutura.getCriadoEm()
        );

        when(repository.findByPacienteId(pacienteId))
                .thenReturn(List.of(consultaFutura, consultaPassada));
        when(mapper.paraHistorico(consultaFutura))
                .thenReturn(historicoFuturo);

        List<ConsultaHistorico> resultado = adapter.buscarConsultasFuturasPorPacienteId(pacienteId, hoje, agora);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(hoje.plusDays(1), resultado.get(0).data());
        verify(repository, times(1)).findByPacienteId(pacienteId);
        verify(mapper, times(1)).paraHistorico(consultaFutura);
        verify(mapper, never()).paraHistorico(consultaPassada);
    }

    @Test
    void buscarConsultasFuturasPorPacienteId_ComConsultaMesmoDiaFutura_DeveIncluir() {
        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();
        LocalTime horarioFuturo = agora.plusHours(1);
        
        ConsultaReadModel consultaHojeFutura = new ConsultaReadModel();
        consultaHojeFutura.setId(UUID.randomUUID());
        consultaHojeFutura.setPacienteId(pacienteId);
        consultaHojeFutura.setAgendaId(UUID.randomUUID());
        consultaHojeFutura.setData(hoje);
        consultaHojeFutura.setHorario(horarioFuturo);
        consultaHojeFutura.setStatus(StatusConsulta.CONFIRMADA);
        consultaHojeFutura.setCriadoEm(LocalDateTime.now());
        
        ConsultaHistorico historicoHojeFuturo = new ConsultaHistorico(
                consultaHojeFutura.getId(),
                pacienteId,
                consultaHojeFutura.getAgendaId(),
                consultaHojeFutura.getData(),
                consultaHojeFutura.getHorario(),
                consultaHojeFutura.getStatus(),
                consultaHojeFutura.getCriadoEm()
        );

        when(repository.findByPacienteId(pacienteId))
                .thenReturn(List.of(consultaHojeFutura));
        when(mapper.paraHistorico(consultaHojeFutura))
                .thenReturn(historicoHojeFuturo);

        List<ConsultaHistorico> resultado = adapter.buscarConsultasFuturasPorPacienteId(pacienteId, hoje, agora);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(hoje, resultado.get(0).data());
        assertTrue(resultado.get(0).horario().isAfter(agora));
    }

    @Test
    void buscarConsultasFuturasPorPacienteId_ComConsultaMesmoDiaPassada_NaoDeveIncluir() {
        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();
        LocalTime horarioPassado = agora.minusHours(1);
        
        ConsultaReadModel consultaHojePassada = new ConsultaReadModel();
        consultaHojePassada.setId(UUID.randomUUID());
        consultaHojePassada.setPacienteId(pacienteId);
        consultaHojePassada.setAgendaId(UUID.randomUUID());
        consultaHojePassada.setData(hoje);
        consultaHojePassada.setHorario(horarioPassado);
        consultaHojePassada.setStatus(StatusConsulta.REALIZADA);
        consultaHojePassada.setCriadoEm(LocalDateTime.now());

        when(repository.findByPacienteId(pacienteId))
                .thenReturn(List.of(consultaHojePassada));

        List<ConsultaHistorico> resultado = adapter.buscarConsultasFuturasPorPacienteId(pacienteId, hoje, agora);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(mapper, never()).paraHistorico(any());
    }
}
