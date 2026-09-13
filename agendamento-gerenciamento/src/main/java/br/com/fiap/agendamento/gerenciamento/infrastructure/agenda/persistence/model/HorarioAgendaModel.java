package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "horarios_agendas")
@Getter
@Setter
public class HorarioAgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "horarios_agendas_seq")
    @SequenceGenerator(name = "horarios_agendas_seq", sequenceName = "horarios_agendas_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID uuid;

    @ManyToOne
    private AgendaModel agenda;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;
}
