package br.com.fiap.agendamento.gerenciamento.application.consulta.usecases;

import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Paciente;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoAutorizadoException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultaHistoricoUseCaseTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void deveBuscarHistoricoDoProprioPaciente() {
        UUID pacienteUuid = UUID.randomUUID();
        ConsultaHistoricoUseCase useCase = useCaseComClockFixo();
        when(usuarioRepository.buscarPorUuid(pacienteUuid)).thenReturn(Optional.of(paciente(pacienteUuid)));
        when(consultaRepository.buscarPorPaciente(pacienteUuid)).thenReturn(List.of());

        var resultado = useCase.buscarHistoricoPorPaciente(pacienteUuid, pacienteAutenticado(pacienteUuid));

        assertThat(resultado).isEmpty();
        verify(consultaRepository).buscarPorPaciente(pacienteUuid);
    }

    @Test
    void deveDelegarFiltroDeConsultasFuturasAoRepositorioComHorarioAtual() {
        UUID pacienteUuid = UUID.randomUUID();
        ConsultaHistoricoUseCase useCase = useCaseComClockFixo();
        when(usuarioRepository.buscarPorUuid(pacienteUuid)).thenReturn(Optional.of(paciente(pacienteUuid)));
        when(consultaRepository.buscarFuturasPorPaciente(eq(pacienteUuid), any(LocalDateTime.class))).thenReturn(List.of());

        useCase.buscarConsultasFuturasPorPaciente(pacienteUuid, pacienteAutenticado(pacienteUuid));

        ArgumentCaptor<LocalDateTime> agora = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(consultaRepository).buscarFuturasPorPaciente(eq(pacienteUuid), agora.capture());
        assertThat(agora.getValue()).isEqualTo(LocalDateTime.of(2026, 9, 7, 12, 0));
    }

    @Test
    void deveImpedirPacienteDeConsultarHistoricoDeOutroPaciente() {
        UUID pacienteAutenticadoUuid = UUID.randomUUID();
        UUID outroPacienteUuid = UUID.randomUUID();
        ConsultaHistoricoUseCase useCase = useCaseComClockFixo();

        assertThatThrownBy(() -> useCase.buscarHistoricoPorPaciente(outroPacienteUuid, pacienteAutenticado(pacienteAutenticadoUuid)))
                .isInstanceOf(UsuarioNaoAutorizadoException.class);

        verify(usuarioRepository, never()).buscarPorUuid(any());
        verify(consultaRepository, never()).buscarPorPaciente(any());
    }

    private ConsultaHistoricoUseCase useCaseComClockFixo() {
        Clock clock = Clock.fixed(Instant.parse("2026-09-07T12:00:00Z"), ZoneOffset.UTC);
        return new ConsultaHistoricoUseCase(consultaRepository, usuarioRepository, clock);
    }

    private UsuarioAutenticado pacienteAutenticado(UUID uuid) {
        return new UsuarioAutenticado(uuid, "Paciente", TipoUsuario.PACIENTE);
    }

    private Paciente paciente(UUID uuid) {
        return new Paciente(uuid, "Paciente", new Email("paciente@fiap.com"), new Telefone("11999999999"), "senha", true, false);
    }
}
