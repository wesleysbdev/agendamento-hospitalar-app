package br.com.fiap.agendamento.gerenciamento.domain.usuario.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class CredenciaisInvalidasException extends RegraDeNegocioException {
    public CredenciaisInvalidasException() {
        super("Credenciais inválidas.");
    }
}
