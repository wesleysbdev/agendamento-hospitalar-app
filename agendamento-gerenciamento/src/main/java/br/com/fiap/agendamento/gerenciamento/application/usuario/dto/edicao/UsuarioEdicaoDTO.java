package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;

import java.util.UUID;

public interface UsuarioEdicaoDTO {
    UUID uuid();
    String nome();
    String email();
}
