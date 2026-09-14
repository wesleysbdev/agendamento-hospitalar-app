package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.enums.StatusConsulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model.AgendaModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.UsuarioModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "consulta", uniqueConstraints = {@UniqueConstraint(name = "uk_consulta_uuid", columnNames = "uuid")})
@Getter
@Setter
public class ConsultaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consulta_seq")
    @SequenceGenerator(name = "consulta_seq", sequenceName = "consulta_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_consulta_paciente"))
    private UsuarioModel paciente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agenda_id", nullable = false, foreignKey = @ForeignKey(name = "fk_consulta_agenda"))
    private AgendaModel agenda;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusConsulta status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

}
