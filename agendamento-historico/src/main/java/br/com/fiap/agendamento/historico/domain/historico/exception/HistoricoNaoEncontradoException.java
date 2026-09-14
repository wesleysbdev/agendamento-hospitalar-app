package br.com.fiap.agendamento.historico.domain.historico.exception;

public class HistoricoNaoEncontradoException extends RuntimeException {
    public HistoricoNaoEncontradoException(String message) {
        super(message);
    }
}
