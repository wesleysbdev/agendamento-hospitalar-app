package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;

import java.util.UUID;

public record PacienteDTO(
        UUID uuid,
        String nome,
        String email,
        boolean ativo,
        TipoUsuario tipo,
        String telefone
) implements UsuarioDTO {
}
