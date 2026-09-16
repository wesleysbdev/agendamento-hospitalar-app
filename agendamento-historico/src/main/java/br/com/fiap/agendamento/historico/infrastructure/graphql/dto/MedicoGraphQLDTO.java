package br.com.fiap.agendamento.historico.infrastructure.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicoGraphQLDTO {

    private String id;
    private String nome;
    private String email;
}
