package br.com.fiap.agendamento.gerenciamento.domain.hospital.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class HospitalDadosInvalidosException extends RegraDeNegocioException {
    public HospitalDadosInvalidosException(String message) {
        super(message);
    }
}
