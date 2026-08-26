package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("PACIENTE")
@Getter
@Setter
public final class PacienteModel extends UsuarioModel {

    @Column(nullable = false, length = 13)
    private String telefone;

}
