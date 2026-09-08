package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.graphql.controller;

import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoHistoricoConsulta;
import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoAutorizadoException;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.graphql.dto.ConsultaHistoricoGraphQLResponse;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.graphql.mapper.ConsultaHistoricoGraphQLMapper;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class HistoricoConsultaGraphQLController {

    private final GestaoHistoricoConsulta gestaoHistoricoConsulta;
    private final SecurityContextProvider securityContextProvider;
    private final ConsultaHistoricoGraphQLMapper mapper;

    @QueryMapping
    public List<ConsultaHistoricoGraphQLResponse> historicoPaciente(@Argument UUID pacienteUuid) {
        return gestaoHistoricoConsulta.buscarHistoricoPorPaciente(
                        pacienteUuid,
                        securityContextProvider.obterUsuarioAutenticado()
                ).stream()
                .map(mapper::paraResponse)
                .toList();
    }

    @QueryMapping
    public List<ConsultaHistoricoGraphQLResponse> consultasFuturas(@Argument UUID pacienteUuid) {
        return gestaoHistoricoConsulta.buscarConsultasFuturasPorPaciente(
                        pacienteUuid,
                        securityContextProvider.obterUsuarioAutenticado()
                ).stream()
                .map(mapper::paraResponse)
                .toList();
    }

    @GraphQlExceptionHandler(UsuarioNaoAutorizadoException.class)
    public GraphQLError handleUsuarioNaoAutorizado(UsuarioNaoAutorizadoException ex) {
        return erro(ex.getMessage(), ErrorType.FORBIDDEN);
    }

    @GraphQlExceptionHandler({RegraDeNegocioException.class, IllegalArgumentException.class})
    public GraphQLError handleRegraDeNegocio(RuntimeException ex) {
        return erro(ex.getMessage(), ErrorType.BAD_REQUEST);
    }

    private GraphQLError erro(String mensagem, ErrorType tipo) {
        return GraphqlErrorBuilder.newError().message(mensagem).errorType(tipo).build();
    }
}
