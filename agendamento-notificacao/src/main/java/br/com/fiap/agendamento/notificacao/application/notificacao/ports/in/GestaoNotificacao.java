package br.com.fiap.agendamento.notificacao.application.notificacao.ports.in;

import br.com.fiap.agendamento.notificacao.application.notificacao.dto.NotificacaoDTO;

public interface GestaoNotificacao {
    void enviarNotificacao(NotificacaoDTO notificacaoDTO);
}
