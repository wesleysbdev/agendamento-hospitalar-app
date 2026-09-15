package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;

import java.util.UUID;

public interface UsuarioDTO {
    UUID id();
    String nome();
    String email();
    boolean ativo();
    TipoUsuario tipo();
}
