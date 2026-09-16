package br.com.fiap.agendamento.historico.infrastructure.persistence.model;

import br.com.fiap.agendamento.historico.domain.consulta.enums.StatusConsulta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "consulta")
@Getter
@Setter
@Immutable
public class ConsultaReadModel {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "paciente_id", nullable = false)
    private UUID pacienteId;

    @Column(name = "agenda_id", nullable = false)
    private UUID agendaId;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "horario", nullable = false)
    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private StatusConsulta status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
