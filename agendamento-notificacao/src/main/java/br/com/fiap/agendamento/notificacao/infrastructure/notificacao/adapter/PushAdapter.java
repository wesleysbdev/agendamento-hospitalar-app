package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.adapter;

import br.com.fiap.agendamento.notificacao.application.dto.NotificacaoConsultaDTO;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.EnvioNotificacao;
import br.com.fiap.agendamento.notificacao.domain.notificacao.entity.Notificacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PushAdapter implements EnvioNotificacao {

    private final Logger log =
            LoggerFactory.getLogger(PushAdapter.class);

    @Override
    public void enviar(NotificacaoConsultaDTO notificacao) {
        log.info(
                "Enviando notificação por push: {}", notificacao
        );
    }
}
