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
                @UniqueConstraint(name = "uk_agenda_horario_uuid", columnNames = "uuid"),
                @UniqueConstraint(name = "uk_agenda_horario_dia_horario", columnNames = {"agenda_id", "dia_semana", "horario"})
        })
@Getter
@Setter
public class HorarioAgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agenda_horario_seq")
    @SequenceGenerator(name = "agenda_horario_seq", sequenceName = "agenda_horario_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID uuid;

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
