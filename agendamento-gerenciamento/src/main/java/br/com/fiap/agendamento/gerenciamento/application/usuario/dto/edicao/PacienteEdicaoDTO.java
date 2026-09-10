package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;

import java.util.UUID;

public record PacienteEdicaoDTO(
        String nome,
        String email,
        String telefone
) implements UsuarioEdicaoDTO {
}
