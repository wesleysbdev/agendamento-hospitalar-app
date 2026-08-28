package br.com.fiap.agendamento.gerenciamento.domain.consulta.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class ConsultaDadosInvalidosException extends RegraDeNegocioException {
    public ConsultaDadosInvalidosException(String message) {
        super(message);
    }
}
