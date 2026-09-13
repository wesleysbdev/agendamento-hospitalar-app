package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model;

import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.UsuarioModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "agendas")
@Getter
@Setter
public class AgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agendas_seq")
    @SequenceGenerator(name = "agendas_seq", sequenceName = "agendas_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID uuid;

    @ManyToOne
    private UsuarioModel medico;

    @ManyToOne
    private HospitalModel hospital;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

//    @ElementCollection
//    @CollectionTable(name = "agenda_horarios", joinColumns = @JoinColumn(name = "agenda_id"))
//    @Column(name = "horario")
//    private List<LocalTime> horarios;
}
