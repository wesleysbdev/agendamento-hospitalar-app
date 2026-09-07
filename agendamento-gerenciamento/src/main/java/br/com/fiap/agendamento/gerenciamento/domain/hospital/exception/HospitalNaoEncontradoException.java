package br.com.fiap.agendamento.gerenciamento.domain.hospital.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class HospitalNaoEncontradoException extends RegraDeNegocioException {
    public HospitalNaoEncontradoException(String message) {
        super(message);
    }
}
