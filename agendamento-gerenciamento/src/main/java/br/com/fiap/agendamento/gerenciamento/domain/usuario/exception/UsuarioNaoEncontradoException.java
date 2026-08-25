package br.com.fiap.agendamento.gerenciamento.domain.usuario.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class UsuarioNaoEncontradoException extends RegraDeNegocioException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
}
