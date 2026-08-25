package br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta.*;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;

import java.util.List;
import java.util.UUID;

public interface GestaoConsultaUsuario {
    List<UsuarioDTO> listarUsuarios(UsuarioAutenticado usuarioAutenticado);

    UsuarioDTO buscarUsuarioPorUuid(UUID uuid, UsuarioAutenticado usuarioAutenticado);

    List<? extends UsuarioDTO> listarPorTipo(UsuarioAutenticado usuarioAutenticado, TipoUsuario tipo);
}
