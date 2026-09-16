package br.com.fiap.agendamento.gerenciamento.application.consulta.usecases;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaDTO;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoConsultaConsulta;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.exception.ConsultaNaoEncontradaException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoAutorizadoException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ConsultaConsultaUseCase implements GestaoConsultaConsulta {

    private final ConsultaRepository repository;
    private final UsuarioRepository usuarioRepository;

    public ConsultaConsultaUseCase(ConsultaRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public static void verificaPermissao(TipoUsuario tipoUsuario, Set<TipoUsuario> tiposPermitidos) {
        if (!tiposPermitidos.contains(tipoUsuario)) {
            throw new UsuarioNaoAutorizadoException();
        }
    }

    @Override
    public List<ConsultaDTO> listarConsultar(UsuarioAutenticado usuarioAutenticado) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO));
        return repository.buscarTodos();
    }

    @Override
    public ConsultaDTO buscarPorId(UUID id, UsuarioAutenticado usuarioAutenticado) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO, TipoUsuario.MEDICO, TipoUsuario.PACIENTE));
        Consulta consulta = buscarConsultaPorId(id);

        if (usuarioAutenticado.tipo() == TipoUsuario.PACIENTE && !consulta.getPaciente().getId().equals(usuarioAutenticado.uuid())) {
            throw new UsuarioNaoAutorizadoException();
        }

        if (usuarioAutenticado.tipo() == TipoUsuario.MEDICO && !consulta.getAgenda().getMedico().getId().equals(usuarioAutenticado.uuid())) {
            throw new UsuarioNaoAutorizadoException();
        }

        return converterParaDTO(consulta);
    }

    @Override
    public List<ConsultaDTO> listarConsultasPorPaciente(UUID id, UsuarioAutenticado usuarioAutenticado) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO, TipoUsuario.PACIENTE));
        if (usuarioAutenticado.tipo() == TipoUsuario.PACIENTE && !usuarioAutenticado.uuid().equals(id)) {
            throw new UsuarioNaoAutorizadoException();
        }
        return repository.buscarPorPacienteId(id);
    }

    @Override
    public List<ConsultaDTO> listarConsultasMedico(UUID id, UsuarioAutenticado usuarioAutenticado) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO, TipoUsuario.MEDICO));
        if (usuarioAutenticado.tipo() == TipoUsuario.MEDICO && !usuarioAutenticado.uuid().equals(id)) {
            throw new UsuarioNaoAutorizadoException();
        }
        return repository.buscarPorMedicoId(id);
    }

    private Consulta buscarConsultaPorId(UUID id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ConsultaNaoEncontradaException());
    }

    private ConsultaDTO converterParaDTO(Consulta consulta) {
        return new ConsultaDTO(
                consulta.getId(),
                consulta.getData().atTime(consulta.getHorario()),
                consulta.getAgenda().getMedico().getNome(),
                consulta.getAgenda().getHospital().getNome(),
                consulta.getAgenda().getHospital().getEndereco(),
                consulta.getStatus()
        );
    }
}
