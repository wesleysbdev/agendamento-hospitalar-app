package br.com.fiap.agendamento.gerenciamento.domain.usuario.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class UsuarioNaoAutorizadoException extends RegraDeNegocioException {
    public UsuarioNaoAutorizadoException() {
        super("Usuário não tem permissão para realizar essa operação");
    }
}
