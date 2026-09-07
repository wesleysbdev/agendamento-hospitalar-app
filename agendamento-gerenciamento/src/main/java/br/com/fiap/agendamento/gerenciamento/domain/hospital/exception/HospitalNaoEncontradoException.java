package br.com.fiap.agendamento.gerenciamento.domain.hospital.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RecursoNaoEncontradoException;

public class HospitalNaoEncontradoException extends RecursoNaoEncontradoException {
    public HospitalNaoEncontradoException(String message) {
        super(message);
    }
}
