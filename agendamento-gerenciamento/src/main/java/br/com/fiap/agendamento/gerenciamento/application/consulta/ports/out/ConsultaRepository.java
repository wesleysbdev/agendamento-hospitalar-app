package br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaDTO;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultaRepository {

    Consulta salvar(Consulta consulta);

    Optional<Consulta> buscarPorId(UUID uuid);

    List<ConsultaDTO> buscarTodos();

    boolean existeConsulta(UUID agendaId, LocalDate data, LocalTime hora);

    List<ConsultaDTO> buscarPorPacienteId(UUID id);

    List<ConsultaDTO> buscarPorMedicoId(UUID id);
}
