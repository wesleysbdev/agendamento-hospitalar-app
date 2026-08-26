package br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    List<Usuario> listar();

    Optional<Usuario> buscarPorUuid(UUID uuid);

    List<Usuario> listarPorTipo(TipoUsuario tipoUsuario);

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(Email email);
}
