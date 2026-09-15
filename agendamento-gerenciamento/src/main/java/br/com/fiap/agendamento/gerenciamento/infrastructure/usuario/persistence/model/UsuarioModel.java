package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(name = "uk_usuario_uuid", columnNames = "uuid")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID uuid;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private boolean ativo;

    @Column(nullable = false)
    private boolean excluido;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

}
