package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao;

import java.util.UUID;

public record EnfermeiroEdicaoDTO(
        UUID uuid,
        String nome,
        String email
) implements UsuarioEdicaoDTO {
}
