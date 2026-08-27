package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto;

import java.util.UUID;

public record UsuarioResponse(
        UUID uuid,
        String nome,
        String email
) {
}
