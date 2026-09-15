package br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultaRepository {

    Consulta salvar(Consulta consulta);

    Optional<Consulta> buscarPorId(UUID uuid);

    List<Consulta> buscarTodos();

    boolean existeConsulta(UUID agendaId, LocalDate data, LocalTime hora);

    List<Consulta> buscarPorPacienteId(UUID id);

    List<Consulta> buscarPorMedicoId(UUID id);
}
