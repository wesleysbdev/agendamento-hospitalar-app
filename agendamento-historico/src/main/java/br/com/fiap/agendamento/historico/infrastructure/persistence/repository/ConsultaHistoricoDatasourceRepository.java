package br.com.fiap.agendamento.historico.infrastructure.persistence.repository;

import br.com.fiap.agendamento.historico.infrastructure.persistence.model.ConsultaHistoricoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultaHistoricoDatasourceRepository extends JpaRepository<ConsultaHistoricoModel, UUID> {

    @Query(value = """
        SELECT c.id, c.paciente_id, c.agenda_id, 
               a.medico_id, a.hospital_id,
               p.nome as paciente_nome, p.email as paciente_email, p.telefone as paciente_telefone,
               m.nome as medico_nome, m.email as medico_email,
               h.nome as hospital_nome,
               c.data, c.horario, c.status, c.criado_em
        FROM consulta c
        JOIN agenda a ON c.agenda_id = a.id
        JOIN usuario m ON a.medico_id = m.id AND m.tipo = 'MEDICO'
        JOIN hospital h ON a.hospital_id = h.id
        JOIN usuario p ON c.paciente_id = p.id AND p.tipo = 'PACIENTE'
        WHERE c.paciente_id = :pacienteId
        """, nativeQuery = true)
    List<ConsultaHistoricoModel> findByPacienteId(@Param("pacienteId") UUID pacienteId);

    @Query(value = """
        SELECT c.id, c.paciente_id, c.agenda_id, 
               a.medico_id, a.hospital_id,
               p.nome as paciente_nome, p.email as paciente_email, p.telefone as paciente_telefone,
               m.nome as medico_nome, m.email as medico_email,
               h.nome as hospital_nome,
               c.data, c.horario, c.status, c.criado_em
        FROM consulta c
        JOIN agenda a ON c.agenda_id = a.id
        JOIN usuario m ON a.medico_id = m.id AND m.tipo = 'MEDICO'
        JOIN hospital h ON a.hospital_id = h.id
        JOIN usuario p ON c.paciente_id = p.id AND p.tipo = 'PACIENTE'
        WHERE c.paciente_id = :pacienteId AND c.data > :dataAtual
        """, nativeQuery = true)
    List<ConsultaHistoricoModel> findFutureByPacienteId(@Param("pacienteId") UUID pacienteId, @Param("dataAtual") LocalDate dataAtual);

    @Query(value = """
        SELECT c.id, c.paciente_id, c.agenda_id, 
               a.medico_id, a.hospital_id,
               p.nome as paciente_nome, p.email as paciente_email, p.telefone as paciente_telefone,
               m.nome as medico_nome, m.email as medico_email,
               h.nome as hospital_nome,
               c.data, c.horario, c.status, c.criado_em
        FROM consulta c
        JOIN agenda a ON c.agenda_id = a.id
        JOIN usuario m ON a.medico_id = m.id AND m.tipo = 'MEDICO'
        JOIN hospital h ON a.hospital_id = h.id
        JOIN usuario p ON c.paciente_id = p.id AND p.tipo = 'PACIENTE'
        WHERE c.paciente_id = :pacienteId AND (c.data < :dataAtual OR (c.data = :dataAtual AND c.horario < :horaAtual))
        """, nativeQuery = true)
    List<ConsultaHistoricoModel> findPastByPacienteId(@Param("pacienteId") UUID pacienteId, 
                                                       @Param("dataAtual") LocalDate dataAtual, 
                                                       @Param("horaAtual") LocalTime horaAtual);
}
