package br.com.fiap.agendamento.gerenciamento.application.usuario.usecases;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.validator.PermissaoValidator;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta.*;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoConsultaUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.application.usuario.validator.UsuarioValidator;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.*;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;

import java.util.List;
import java.util.UUID;

public class ConsultaUsuarioUseCase implements GestaoConsultaUsuario {

    private final UsuarioRepository repository;

    public ConsultaUsuarioUseCase(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UsuarioDTO> listarUsuarios(UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.admin(usuarioAutenticado.tipo());
        List<Usuario> usuarios = repository.listar();
        return usuarios.stream().map(this::converterParaDTO).toList();
    }

    @Override
    public UsuarioDTO buscarUsuarioPorUuid(UUID uuid, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.apenasOProprio(usuarioAutenticado, usuarioAutenticado.uuid());
        Usuario usuario = UsuarioValidator.buscarUsuarioPorUuid(uuid);
        return converterParaDTO(usuario);
    }

    @Override
    public List<? extends UsuarioDTO> listarPorTipo(UsuarioAutenticado usuarioAutenticado, TipoUsuario tipoUsuario) {

        if (tipoUsuario.equals(TipoUsuario.ADMINISTRADOR) || tipoUsuario.equals(TipoUsuario.ENFERMEIRO)) {
            PermissaoValidator.admin(usuarioAutenticado.tipo());
        }

        if (tipoUsuario.equals(TipoUsuario.PACIENTE)) {
            PermissaoValidator.medicoEEnfermeiro(usuarioAutenticado.tipo());
        }

        PermissaoValidator.admin(usuarioAutenticado.tipo());

        List<Usuario> usuarios = repository.listarPorTipo(tipoUsuario);
        return usuarios.stream().map(this::converterParaDTO).toList();
    }

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        return switch (usuario) {
            case Administrador adm -> new AdministradorDTO(
                    adm.getUuid(),
                    adm.getNome(),
                    adm.getEmail().valor(),
                    adm.isAtivo(),
                    adm.getTipo()
            );
            case Enfermeiro enfermeiro -> new EnfermeiroDTO(
                    enfermeiro.getUuid(),
                    enfermeiro.getNome(),
                    enfermeiro.getEmail().valor(),
                    enfermeiro.isAtivo(),
                    enfermeiro.getTipo()
            );
            case Medico med -> new MedicoDTO(
                    med.getUuid(),
                    med.getNome(),
                    med.getEmail().valor(),
                    med.isAtivo(),
                    med.getTipo(),
                    med.getCrm().toString()
            );
            case Paciente pac -> new PacienteDTO(
                    pac.getUuid(),
                    pac.getNome(),
                    pac.getEmail().valor(),
                    pac.isAtivo(),
                    pac.getTipo(),
                    pac.getTelefone().valor()
            );
            default ->
                    throw new IllegalArgumentException("Tipo de usuário desconhecido: " + usuario.getClass().getName());
        };
    }
}