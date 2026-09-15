package br.com.fiap.agendamento.gerenciamento.domain.agenda.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;

public class HorarioAgendaNaoEncontradoException extends RegraDeNegocioException {
    public HorarioAgendaNaoEncontradoException() {
        super("Horario não encontrado");
    }
}
