package br.com.fiap.agendamento.historico.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historico_consultas")
@Getter
@Setter
public class HistoricoConsultaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "historico_consulta_id_seq")
    @SequenceGenerator(name = "historico_consulta_id_seq", sequenceName = "historico_consulta_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID consultaUuid;

    @Column(nullable = false)
    private LocalDateTime horario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoConsultaModel estado;

    @Column(nullable = false, length = 36)
    private UUID pacienteUuid;

    @Column(nullable = false, length = 200)
    private String pacienteNome;

    @Column(nullable = false, length = 36)
    private UUID medicoUuid;

    @Column(nullable = false, length = 200)
    private String medicoNome;

    @Column(nullable = false, length = 10)
    private String medicoCrm;

    @Column(nullable = false, length = 36)
    private UUID hospitalUuid;

    @Column(nullable = false, length = 200)
    private String hospitalNome;

    @Column(nullable = false, length = 255)
    private String hospitalEndereco;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    public enum EstadoConsultaModel {
        AGENDADA, CONFIRMADA, CANCELADA, AUSENTE, REALIZADA
    }
}
