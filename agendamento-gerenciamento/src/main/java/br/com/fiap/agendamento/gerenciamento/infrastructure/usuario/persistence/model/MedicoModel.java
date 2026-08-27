package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("MEDICO")
@Getter
@Setter
public class MedicoModel extends UsuarioModel {

    @Column(unique = true, length = 10)
    private String crm;

}
