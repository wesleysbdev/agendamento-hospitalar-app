package br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConsultaRepository {

    Consulta salvar(Consulta consulta);

    List<Consulta> buscarPorPaciente(UUID pacienteUuid);

    List<Consulta> buscarFuturasPorPaciente(UUID pacienteUuid, LocalDateTime agora);
}
