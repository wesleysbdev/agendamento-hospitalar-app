package br.com.fiap.agendamento.historico.domain.consulta.exception;

public class AcessoNaoAutorizadoException extends RuntimeException {

    public AcessoNaoAutorizadoException(String mensagem) {
        super(mensagem);
    }
}
