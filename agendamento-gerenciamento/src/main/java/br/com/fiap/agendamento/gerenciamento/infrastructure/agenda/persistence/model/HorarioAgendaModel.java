package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(
        name = "agenda_horario",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_agenda_horario_id", columnNames = "id"),
                @UniqueConstraint(name = "uk_agenda_horario_dia_horario", columnNames = {"agenda_id", "dia_semana", "horario"})
        })
@Getter
@Setter
public class HorarioAgendaModel {

    @Id
    @Column(nullable = false, unique = true, updatable = false, length = 36)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agenda_id", nullable = false, foreignKey = @ForeignKey(name = "fk_agenda_horario_agenda"))
    private AgendaModel agenda;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false, length = 9)
    private DayOfWeek diaSemana;

    @Column(nullable = false)
    private LocalTime horario;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;
}
