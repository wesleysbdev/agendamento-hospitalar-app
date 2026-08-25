package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Crm;

import java.util.UUID;

public record MedicoEdicaoDTO(
        UUID uuid,
        String nome,
        String email,
        String crm
) implements UsuarioEdicaoDTO {
}
