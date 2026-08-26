package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("ENFERMEIRO")
@Getter
@Setter
public final class EnfermeiroModel extends UsuarioModel {
}
