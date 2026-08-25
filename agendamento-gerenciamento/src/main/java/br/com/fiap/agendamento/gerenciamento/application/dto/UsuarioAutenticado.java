package br.com.fiap.agendamento.gerenciamento.application.dto;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;

import java.util.UUID;

public record UsuarioAutenticado(
        UUID uuid,
        String nome,
        TipoUsuario tipo
) {
}
