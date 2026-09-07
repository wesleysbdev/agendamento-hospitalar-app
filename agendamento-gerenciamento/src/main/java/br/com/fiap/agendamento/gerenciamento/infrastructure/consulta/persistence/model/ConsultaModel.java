package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model;

import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.MedicoModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.PacienteModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "consultas")
@Getter
@Setter
public class ConsultaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "consulta_id_seq")
    @SequenceGenerator(name = "consulta_id_seq", sequenceName = "consulta_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime horario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medico_id", nullable = false)
    private MedicoModel medico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteModel paciente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalModel hospital;

    @Column(nullable = false)
    private String status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;
}
