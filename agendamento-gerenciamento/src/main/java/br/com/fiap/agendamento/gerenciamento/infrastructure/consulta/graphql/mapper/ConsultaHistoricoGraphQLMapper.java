package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.graphql.mapper;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaHistoricoDTO;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.graphql.dto.ConsultaHistoricoGraphQLResponse;
import org.springframework.stereotype.Component;

@Component
public class ConsultaHistoricoGraphQLMapper {

    public ConsultaHistoricoGraphQLResponse paraResponse(ConsultaHistoricoDTO consulta) {
        return new ConsultaHistoricoGraphQLResponse(
                consulta.uuid().toString(),
                consulta.horario().toString(),
                consulta.medicoUuid().toString(),
                consulta.medicoNome(),
                consulta.hospitalUuid().toString(),
                consulta.hospitalNome(),
                consulta.hospitalEndereco(),
                consulta.estado().name()
        );
    }
}
