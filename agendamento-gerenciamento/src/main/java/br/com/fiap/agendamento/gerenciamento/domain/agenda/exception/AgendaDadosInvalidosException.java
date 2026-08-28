package br.com.fiap.agendamento.gerenciamento.domain.agenda.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class AgendaDadosInvalidosException extends RegraDeNegocioException {
    public AgendaDadosInvalidosException(String message) {
        super(message);
    }
}
