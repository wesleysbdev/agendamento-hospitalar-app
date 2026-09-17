package br.com.fiap.agendamento.notificacao.domain.notificacao.exception;

import br.com.fiap.agendamento.notificacao.domain.exception.RegraDeNegocioException;

public class NotificacaoDadosInvalidosException extends RegraDeNegocioException {
    public NotificacaoDadosInvalidosException(String message) {
        super(message);
    }
}
