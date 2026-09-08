package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.repository;

import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model.ConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultaDatasourceRepository extends JpaRepository<ConsultaModel, Long> {

    @Query("""
            select consulta from ConsultaModel consulta
            join fetch consulta.medico
            join fetch consulta.paciente
            join fetch consulta.hospital
            where consulta.paciente.uuid = :pacienteUuid
            order by consulta.horario desc
            """)
    List<ConsultaModel> findHistoricoByPacienteUuid(@Param("pacienteUuid") UUID pacienteUuid);

    @Query("""
            select consulta from ConsultaModel consulta
            join fetch consulta.medico
            join fetch consulta.paciente
            join fetch consulta.hospital
            where consulta.paciente.uuid = :pacienteUuid
              and consulta.horario > :agora
            order by consulta.horario asc
            """)
    List<ConsultaModel> findFuturasByPacienteUuid(
            @Param("pacienteUuid") UUID pacienteUuid,
            @Param("agora") LocalDateTime agora
    );
}
