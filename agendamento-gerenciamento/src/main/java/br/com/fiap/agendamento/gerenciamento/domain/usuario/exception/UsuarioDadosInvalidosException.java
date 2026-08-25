package br.com.fiap.agendamento.gerenciamento.domain.usuario.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class UsuarioDadosInvalidosException extends RegraDeNegocioException {
    public UsuarioDadosInvalidosException(String message) {
        super(message);
    }
}
