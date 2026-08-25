package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao;

import java.util.UUID;

public record AlteracaoSenhaDTO(
        UUID usuarioUuid,
        String senhaNova
) {
}
