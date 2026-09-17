package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.adapter;

import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.NotificacaoRepository;
import br.com.fiap.agendamento.notificacao.domain.notificacao.entity.Notificacao;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.mapper.NotificacaoModelMapper;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.model.NotificacaoModel;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.repository.NotificacaoDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class NotificacaoRepositoryAdapter implements NotificacaoRepository {

    private final NotificacaoDatasourceRepository repository;
    private final NotificacaoModelMapper mapper;

    @Override
    public List<Notificacao> listar() {
        return repository.findAll().stream().map(mapper::paraEntidade).toList();
    }

    @Override
    public Optional<Notificacao> buscarPorUuid(UUID uuid) {
        return repository.findById(uuid).map(mapper::paraEntidade);
    }

    @Override
    public Notificacao salvar(Notificacao notificacao) {
        NotificacaoModel model = repository.findById(notificacao.getUuid())
                .map(existente -> {
                    mapper.atualizarModelo(notificacao, existente);
                    return existente;
                }).orElseGet(() -> mapper.paraModelo(notificacao));

        NotificacaoModel salvo = repository.save(model);
        return mapper.paraEntidade(salvo);
    }
}
