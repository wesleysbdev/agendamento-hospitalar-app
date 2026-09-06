package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.repository;

import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.model.NotificacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificacaoDatasourceRepository extends JpaRepository<NotificacaoModel, Long> {
    Optional<NotificacaoModel> findByUuid(UUID uuid);

    List<NotificacaoModel> findByDestinatario(String destinatario);
}
