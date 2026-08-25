package br.com.fiap.agendamento.gerenciamento.domain.exception;

public class RegraDeNegocioException extends RuntimeException {
    public RegraDeNegocioException(String message) {
        super(message);
    }
}
