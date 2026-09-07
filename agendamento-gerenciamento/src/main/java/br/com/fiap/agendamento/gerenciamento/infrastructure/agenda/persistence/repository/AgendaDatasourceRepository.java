package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.repository;

import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model.AgendaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendaDatasourceRepository extends JpaRepository<AgendaModel, Long> {
    Optional<AgendaModel> findByUuid(UUID uuid);

    List<AgendaModel> findByMedicoUuid(UUID medicoUuid);

    List<AgendaModel> findByHospitalUuid(UUID hospitalUuid);
}
