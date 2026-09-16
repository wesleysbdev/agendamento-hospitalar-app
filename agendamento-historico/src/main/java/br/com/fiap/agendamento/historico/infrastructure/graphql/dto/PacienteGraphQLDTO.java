package br.com.fiap.agendamento.historico.infrastructure.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteGraphQLDTO {

    private String id;
    private String nome;
    private String email;
    private String telefone;
}
