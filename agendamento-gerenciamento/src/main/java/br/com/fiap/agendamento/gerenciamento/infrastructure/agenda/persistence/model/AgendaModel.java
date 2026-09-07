package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "agenda")
@Getter
@Setter
public class AgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agenda_seq")
    @SequenceGenerator(name = "agenda_seq", sequenceName = "agenda_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID uuid;

    @Column(nullable = false, length = 36)
    private UUID medicoUuid;

    @Column(nullable = false, length = 36)
    private UUID hospitalUuid;

    @ElementCollection
    @CollectionTable(name = "agenda_horarios", joinColumns = @JoinColumn(name = "agenda_id"))
    @Column(name = "horario")
    private List<LocalTime> horarios;
}
