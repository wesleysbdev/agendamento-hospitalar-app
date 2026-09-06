package br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;

public interface ConsultaRepository {

    Consulta salvar(Consulta consulta);
}
