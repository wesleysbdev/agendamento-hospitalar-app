package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.repository;

import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model.AgendaModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendaDatasourceRepository extends JpaRepository<AgendaModel, UUID> {

    @EntityGraph(attributePaths = {"horarios", "medico", "hospital"})
    List<AgendaModel> findByMedicoId(UUID medicoId);

    @EntityGraph(attributePaths = {"horarios", "medico", "hospital"})
    List<AgendaModel> findByHospitalId(UUID hospitalId);

    @EntityGraph(attributePaths = {"horarios", "medico", "hospital"})
    Optional<AgendaModel> findByMedicoIdAndHospitalId(UUID medicoId, UUID hospitalId);

    @EntityGraph(attributePaths = {"horarios", "medico", "hospital"})
    @Query("SELECT a FROM AgendaModel a WHERE a.id = :id")
    Optional<AgendaModel> findWithRelacionamentosById(UUID id);

    @EntityGraph(attributePaths = {"horarios", "medico", "hospital"})
    @Query("SELECT a FROM AgendaModel a")
    List<AgendaModel> findAllWithRelacionamentos();
}
