package br.com.fiap.agendamento.historico.infrastructure.persistence.repository;

import br.com.fiap.agendamento.historico.infrastructure.persistence.model.HistoricoConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoricoDatasourceRepository extends JpaRepository<HistoricoConsultaModel, Long> {

    List<HistoricoConsultaModel> findByPacienteUuid(UUID pacienteUuid);

    @Query("SELECT h FROM HistoricoConsultaModel h WHERE h.pacienteUuid = :pacienteUuid AND h.horario > :horario")
    List<HistoricoConsultaModel> findByPacienteUuidAndHorarioAfter(@Param("pacienteUuid") UUID pacienteUuid, @Param("horario") LocalDateTime horario);
}
