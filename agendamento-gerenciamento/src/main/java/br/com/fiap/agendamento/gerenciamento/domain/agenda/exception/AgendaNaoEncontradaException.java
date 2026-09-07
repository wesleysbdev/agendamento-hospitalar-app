package br.com.fiap.agendamento.gerenciamento.domain.agenda.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class AgendaNaoEncontradaException extends RegraDeNegocioException {
    public AgendaNaoEncontradaException(String message) {
        super(message);
    }
}
