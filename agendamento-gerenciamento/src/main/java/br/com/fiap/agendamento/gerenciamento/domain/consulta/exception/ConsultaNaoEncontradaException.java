package br.com.fiap.agendamento.gerenciamento.domain.consulta.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class ConsultaNaoEncontradaException extends RegraDeNegocioException {

    public ConsultaNaoEncontradaException() {
        super("Consulta não encontrada.");
    }

    public ConsultaNaoEncontradaException(String message) {
        super(message);

    }
}
