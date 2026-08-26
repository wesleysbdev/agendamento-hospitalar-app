package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract sealed class UsuarioModel permits MedicoModel, PacienteModel, AdministradorModel, EnfermeiroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean ativo;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean excluido;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Column(nullable = false, unique = true, length = 36)
    private UUID uuid;
}
