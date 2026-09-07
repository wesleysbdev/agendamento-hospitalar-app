package br.com.fiap.agendamento.notificacao.application.notificacao.ports.out;

import br.com.fiap.agendamento.notificacao.application.dto.NotificacaoConsultaDTO;

public interface EnvioNotificacao {
    void enviar(NotificacaoConsultaDTO notificacao);
}
