package br.com.fiap.agendamento.notificacao.application.notificacao.usecases;

import br.com.fiap.agendamento.notificacao.application.notificacao.dto.NotificacaoDTO;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.in.GestaoNotificacao;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.EnvioNotificacao;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.NotificacaoRepository;
import br.com.fiap.agendamento.notificacao.domain.notificacao.entity.Notificacao;

import java.util.UUID;

public class EnvioNotificacaoUseCase implements GestaoNotificacao {

    private final NotificacaoRepository repository;

    public EnvioNotificacaoUseCase(NotificacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void enviarNotificacao(NotificacaoDTO notificacaoDTO) {
        Notificacao notificacao = new Notificacao(
                UUID.randomUUID(),
                notificacaoDTO.destinatario(),
                notificacaoDTO.assunto(),
                notificacaoDTO.mensagem(),
                null,
                false
        );

        notificacao.marcarComoEnviada();
        repository.salvar(notificacao);
    }
}
