package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.repository;

import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model.AgendaModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendaDatasourceRepository extends JpaRepository<AgendaModel, Long> {

    @EntityGraph(attributePaths = {"horarios", "medico", "hospital"})
    Optional<AgendaModel> findByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"horarios", "medico", "hospital"})
    List<AgendaModel> findByMedicoUuid(UUID medicoUuid);

    @EntityGraph(attributePaths = {"horarios", "medico", "hospital"})
    List<AgendaModel> findByHospitalUuid(UUID hospitalUuid);

    @EntityGraph(attributePaths = {"horarios", "medico", "hospital"})
    Optional<AgendaModel> findByMedicoUuidAndHospitalUuid(UUID medicoUuid, UUID hospitalUuid);
}
