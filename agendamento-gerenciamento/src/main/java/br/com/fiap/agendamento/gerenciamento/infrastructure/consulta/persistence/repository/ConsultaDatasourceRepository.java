package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.repository;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.enums.StatusConsulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model.ConsultaModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsultaDatasourceRepository extends JpaRepository<ConsultaModel, UUID> {

    @EntityGraph(attributePaths = {"paciente", "agenda", "agenda.medico", "agenda.hospital", "agenda.horarios"})
    @Query("""
            SELECT c
            FROM ConsultaModel c
            WHERE c.id = :id
            """)
    Optional<ConsultaModel> findWithRelacionamentosById(@Param("id") UUID id);

    @EntityGraph(attributePaths = {"paciente", "agenda", "agenda.medico", "agenda.hospital"})
    @Query("""
            SELECT c
            FROM ConsultaModel c
            """)
    List<ConsultaModel> findAllWithRelacionamentos();

    @EntityGraph(attributePaths = {"paciente", "agenda", "agenda.medico", "agenda.hospital"})
    @Query("""
            SELECT c
            FROM ConsultaModel c
            WHERE c.paciente.id = :id
            """)
    List<ConsultaModel> findByPacienteId(@Param("id") UUID id);

    @EntityGraph(attributePaths = {"paciente", "agenda", "agenda.medico", "agenda.hospital"})
    @Query("""
            SELECT c
            FROM ConsultaModel c
            WHERE c.agenda.medico.id = :id
            """)
    List<ConsultaModel> findByMedicoId(@Param("id") UUID id);

    @Query("""
            SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
            FROM ConsultaModel c
            WHERE c.agenda.id = :agendaId
              AND c.data = :data
              AND c.horario = :horario
              AND c.status IN :status
            """)
    boolean existeConsulta(@Param("agendaId") UUID agendaId, @Param("data") LocalDate data, @Param("horario") LocalTime horario, @Param("status") Collection<StatusConsulta> status);
}