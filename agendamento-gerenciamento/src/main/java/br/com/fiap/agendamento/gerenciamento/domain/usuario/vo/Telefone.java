package br.com.fiap.agendamento.gerenciamento.domain.usuario.vo;

import br.com.fiap.agendamento.gerenciamento.domain.constants.UsuarioConstants;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;

public record Telefone(String valor) {
    public Telefone {
        if (valor == null || !valor.matches(UsuarioConstants.REGEX_TELEFONE_BR)) {
            throw new UsuarioDadosInvalidosException("Telefone em formato inválido.");
        }
    }
}
