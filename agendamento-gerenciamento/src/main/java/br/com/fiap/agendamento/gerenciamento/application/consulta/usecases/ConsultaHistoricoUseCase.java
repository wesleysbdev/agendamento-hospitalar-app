package br.com.fiap.agendamento.gerenciamento.application.consulta.usecases;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaHistoricoDTO;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoHistoricoConsulta;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.application.usuario.validator.PermissaoValidator;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Paciente;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoEncontradoException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ConsultaHistoricoUseCase implements GestaoHistoricoConsulta {

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final Clock clock;

    public ConsultaHistoricoUseCase(
            ConsultaRepository consultaRepository,
            UsuarioRepository usuarioRepository,
            Clock clock
    ) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
        this.clock = clock;
    }

    @Override
    public List<ConsultaHistoricoDTO> buscarHistoricoPorPaciente(UUID pacienteUuid, UsuarioAutenticado usuarioAutenticado) {
        validarPacienteEPermissao(pacienteUuid, usuarioAutenticado);
        return consultaRepository.buscarPorPaciente(pacienteUuid).stream().map(this::paraDTO).toList();
    }

    @Override
    public List<ConsultaHistoricoDTO> buscarConsultasFuturasPorPaciente(UUID pacienteUuid, UsuarioAutenticado usuarioAutenticado) {
        validarPacienteEPermissao(pacienteUuid, usuarioAutenticado);
        return consultaRepository.buscarFuturasPorPaciente(pacienteUuid, LocalDateTime.now(clock)).stream().map(this::paraDTO).toList();
    }

    private void validarPacienteEPermissao(UUID pacienteUuid, UsuarioAutenticado usuarioAutenticado) {
        if (pacienteUuid == null) {
            throw new IllegalArgumentException("A identificação do paciente é obrigatória.");
        }

        PermissaoValidator.historicoPaciente(usuarioAutenticado, pacienteUuid);
        usuarioRepository.buscarPorUuid(pacienteUuid)
                .filter(Paciente.class::isInstance)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Paciente não encontrado."));
    }

    private ConsultaHistoricoDTO paraDTO(Consulta consulta) {
        return new ConsultaHistoricoDTO(
                consulta.getUuid(),
                consulta.getHorario(),
                consulta.getMedico().getUuid(),
                consulta.getMedico().getNome(),
                consulta.getHospital().getUuid(),
                consulta.getHospital().getNome(),
                consulta.getHospital().getEndereco(),
                consulta.getEstado()
        );
    }
}
