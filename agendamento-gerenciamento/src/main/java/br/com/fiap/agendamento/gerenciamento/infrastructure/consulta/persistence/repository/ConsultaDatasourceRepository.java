package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.repository;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model.ConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultaDatasourceRepository extends JpaRepository<ConsultaModel, UUID> {

    @Query("SELECT c FROM ConsultaModel c WHERE c.agenda_id = :agendaId AND c.data = :data AND c.horario = :hora AND c.status = 'AGENDADA' OR c.status = 'CONFIRMADA'")
    boolean existeConsulta(UUID agendaId, LocalDate data, LocalTime hora);

    List<ConsultaModel> findByPacienteId(UUID id);

    List<ConsultaModel> findByMedicoId(UUID id);
}
