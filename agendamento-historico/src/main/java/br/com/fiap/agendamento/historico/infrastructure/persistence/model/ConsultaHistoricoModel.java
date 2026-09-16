package br.com.fiap.agendamento.historico.infrastructure.persistence.model;

import br.com.fiap.agendamento.historico.domain.consulta.enums.StatusConsulta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "consulta")
@Getter
@Setter
public class ConsultaHistoricoModel {

    @Id
    @Column(nullable = false, unique = true, updatable = false, length = 36)
    private UUID id;

    @Column(name = "paciente_id", nullable = false)
    private UUID pacienteId;

    @Column(name = "agenda_id", nullable = false)
    private UUID agendaId;

    @Column(name = "medico_id", nullable = false, insertable = false, updatable = false)
    private UUID medicoId;

    @Column(name = "hospital_id", nullable = false, insertable = false, updatable = false)
    private UUID hospitalId;

    @Column(name = "paciente_nome", nullable = false, insertable = false, updatable = false)
    private String pacienteNome;

    @Column(name = "paciente_email", nullable = false, insertable = false, updatable = false)
    private String pacienteEmail;

    @Column(name = "paciente_telefone", insertable = false, updatable = false)
    private String pacienteTelefone;

    @Column(name = "medico_nome", nullable = false, insertable = false, updatable = false)
    private String medicoNome;

    @Column(name = "medico_email", nullable = false, insertable = false, updatable = false)
    private String medicoEmail;

    @Column(name = "hospital_nome", nullable = false, insertable = false, updatable = false)
    private String hospitalNome;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @Column(nullable = false, length = 10)
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
