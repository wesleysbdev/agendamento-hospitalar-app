package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.model;

import br.com.fiap.agendamento.notificacao.domain.notificacao.enums.TipoNotificacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notificacao", uniqueConstraints = {@UniqueConstraint(name = "uk_notificacao_uuid", columnNames = "uuid")})
@Getter
@Setter
public class NotificacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificacao_seq")
    @SequenceGenerator(name = "notificacao_seq", sequenceName = "notificacao_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID uuid;

    @Column(nullable = false, length = 200)
    private String destinatario;

    @Column(nullable = false, length = 200)
    private String assunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoNotificacao tipo;

    @Column
    private LocalDateTime dataEnvio;

    @Column(nullable = false)
    private boolean enviada;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

}
