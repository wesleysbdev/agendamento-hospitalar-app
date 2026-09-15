package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model;

import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.MedicoModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "agenda", uniqueConstraints = {@UniqueConstraint(name = "uk_agenda_uuid", columnNames = "uuid")})
@Getter
@Setter
public class AgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agenda_seq")
    @SequenceGenerator(name = "agenda_seq", sequenceName = "agenda_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medico_id", nullable = false, foreignKey = @ForeignKey(name = "fk_agenda_medico"))
    private MedicoModel medico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hospital_id", nullable = false, foreignKey = @ForeignKey(name = "fk_agenda_hospital"))
    private HospitalModel hospital;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioAgendaModel> horarios = new ArrayList<>();
}
