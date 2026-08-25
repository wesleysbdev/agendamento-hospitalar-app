package br.com.fiap.agendamento.gerenciamento.domain.usuario.vo;

import br.com.fiap.agendamento.gerenciamento.domain.constants.UsuarioConstants;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;

public record Email(String valor) {
    public Email {
        if (valor == null || !valor.matches(UsuarioConstants.REGEX_EMAIL)) {
            throw new UsuarioDadosInvalidosException("E-mail em formato inválido.");
        }
    }
}
