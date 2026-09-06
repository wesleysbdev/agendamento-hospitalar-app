package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.web.mapper;

import br.com.fiap.agendamento.notificacao.application.notificacao.dto.NotificacaoDTO;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.web.dto.NotificacaoRequest;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoMapper {

    public NotificacaoDTO paraDto(NotificacaoRequest request) {
        return new NotificacaoDTO(
                request.destinatario(),
                request.assunto(),
                request.mensagem(),
                request.tipo()
        );
    }
}
