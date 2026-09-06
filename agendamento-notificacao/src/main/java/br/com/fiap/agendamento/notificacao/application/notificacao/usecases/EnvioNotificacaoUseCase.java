package br.com.fiap.agendamento.notificacao.application.notificacao.usecases;

import br.com.fiap.agendamento.notificacao.application.notificacao.dto.NotificacaoDTO;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.in.GestaoNotificacao;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.EnvioNotificacao;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.NotificacaoRepository;
import br.com.fiap.agendamento.notificacao.domain.notificacao.entity.Notificacao;

import java.util.UUID;

public class EnvioNotificacaoUseCase implements GestaoNotificacao {

    private final NotificacaoRepository repository;
    private final EnvioNotificacao envioNotificacao;

    public EnvioNotificacaoUseCase(NotificacaoRepository repository, EnvioNotificacao envioNotificacao) {
        this.repository = repository;
        this.envioNotificacao = envioNotificacao;
    }

    @Override
    public void enviarNotificacao(NotificacaoDTO notificacaoDTO) {
        Notificacao notificacao = new Notificacao(
                UUID.randomUUID(),
                notificacaoDTO.destinatario(),
                notificacaoDTO.assunto(),
                notificacaoDTO.mensagem(),
                notificacaoDTO.tipo(),
                null,
                false
        );

        envioNotificacao.enviar(notificacao);
        notificacao.marcarComoEnviada();
        repository.salvar(notificacao);
    }
}
