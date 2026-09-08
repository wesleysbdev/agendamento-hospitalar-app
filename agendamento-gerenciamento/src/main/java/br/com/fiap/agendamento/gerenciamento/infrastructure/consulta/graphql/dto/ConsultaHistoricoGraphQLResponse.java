package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.graphql.dto;

public record ConsultaHistoricoGraphQLResponse(
        String uuid,
        String horario,
        String medicoUuid,
        String medicoNome,
        String hospitalUuid,
        String hospitalNome,
        String hospitalEndereco,
        String estado
) {
}
