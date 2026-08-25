package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;

import java.util.UUID;

public record PacienteEdicaoDTO(
        UUID uuid,
        String nome,
        String email,
        String telefone
) implements UsuarioEdicaoDTO {
}
