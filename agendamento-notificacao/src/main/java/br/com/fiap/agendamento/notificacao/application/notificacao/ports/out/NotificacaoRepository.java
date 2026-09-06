package br.com.fiap.agendamento.notificacao.application.notificacao.ports.out;

import br.com.fiap.agendamento.notificacao.domain.notificacao.entity.Notificacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificacaoRepository {
    List<Notificacao> listar();

    Optional<Notificacao> buscarPorUuid(UUID uuid);

    Notificacao salvar(Notificacao notificacao);
}
