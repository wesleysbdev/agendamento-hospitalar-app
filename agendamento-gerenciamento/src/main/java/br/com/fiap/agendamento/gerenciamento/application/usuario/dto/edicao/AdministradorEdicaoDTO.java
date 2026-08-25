package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao;

import java.util.UUID;

public record AdministradorEdicaoDTO(
        UUID uuid,
        String nome,
        String email
) implements UsuarioEdicaoDTO {
}
