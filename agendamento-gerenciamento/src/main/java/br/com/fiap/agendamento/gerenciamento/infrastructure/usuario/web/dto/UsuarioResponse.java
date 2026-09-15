package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;

import java.util.UUID;

public record UsuarioResponse(
        UUID uuid,
        String nome,
        String email,
        TipoUsuario tipo,
        boolean ativo
) {
}
