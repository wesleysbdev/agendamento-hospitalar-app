package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.mapper;

import br.com.fiap.agendamento.notificacao.domain.notificacao.entity.Notificacao;
import br.com.fiap.agendamento.notificacao.domain.notificacao.enums.TipoNotificacao;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.model.NotificacaoModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotificacaoModelMapper {

    default TipoNotificacao paraTipoNotificacao(NotificacaoModel.TipoNotificacaoModel tipoModel) {
        return tipoModel == null ? null : TipoNotificacao.valueOf(tipoModel.name());
    }

    default NotificacaoModel.TipoNotificacaoModel paraTipoNotificacaoModel(TipoNotificacao tipo) {
        return tipo == null ? null : NotificacaoModel.TipoNotificacaoModel.valueOf(tipo.name());
    }

    Notificacao paraEntidade(NotificacaoModel model);

    NotificacaoModel paraModelo(Notificacao entidade);

    void atualizarModelo(Notificacao entidade, @MappingTarget NotificacaoModel existente);
}
