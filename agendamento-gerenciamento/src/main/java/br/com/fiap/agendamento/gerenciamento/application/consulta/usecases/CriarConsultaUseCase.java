package br.com.fiap.agendamento.gerenciamento.application.consulta.usecases;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaEvent;
import br.com.fiap.agendamento.gerenciamento.application.consulta.enums.ConsultaEventType;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoCadastroConsulta;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaEventPublisher;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;

import java.time.LocalDateTime;
import java.util.UUID;

public class CriarConsultaUseCase implements GestaoCadastroConsulta {

    private final ConsultaRepository consultaRepository;
    private final ConsultaEventPublisher consultaEventPublisher;
    private final SecurityContextProvider securityContextProvider;

    public CriarConsultaUseCase(
            ConsultaRepository consultaRepository,
            ConsultaEventPublisher consultaEventPublisher,
            SecurityContextProvider securityContextProvider
    ) {
        this.consultaRepository = consultaRepository;
        this.consultaEventPublisher = consultaEventPublisher;
        this.securityContextProvider = securityContextProvider;
    }

    // TODO: Implementar parâmetros do request
    @Override
    public Consulta cadastrarConsulta() {
        // TODO: Implementar lógica de criação de consulta
        consultaEventPublisher.publicar(
                new ConsultaEvent(
                        ConsultaEventType.CONSULTA_CRIADA,
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        LocalDateTime.now()
                )
        );
        return null;
    }

    @Override
    public Consulta atualizarConsulta(Consulta consulta) {
        // TODO: Implementar lógica de atualização de consulta
        consultaEventPublisher.publicarAtualizacao(
                new ConsultaEvent(
                        ConsultaEventType.CONSULTA_ATUALIZADA,
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        LocalDateTime.now()
                )
        );
        return null;
    }
}
