package br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.consulta.AgendaDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta.PacienteDTO;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;

public interface GestaoCadastroConsulta {

    // TODO: Implementar parâmetros do request
    Consulta cadastrarConsulta();

    Consulta atualizarConsulta(Consulta consulta);
}
