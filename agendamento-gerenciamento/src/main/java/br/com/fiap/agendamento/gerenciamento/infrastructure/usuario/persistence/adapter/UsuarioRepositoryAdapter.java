package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.adapter;

import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.*;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.mapper.UsuarioModelMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.UsuarioModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.repository.UsuarioDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioDatasourceRepository repository;
    private final UsuarioModelMapper mapper;

    @Override
    public List<Usuario> listar() {
        return repository.findAll().stream().map(mapper::paraEntidade).toList();
    }

    @Override
    public Optional<Usuario> buscarPorUuid(UUID uuid) {
        return repository.findByUuid(uuid).map(mapper::paraEntidade);
    }

    @Override
    public List<Usuario> listarPorTipo(TipoUsuario tipoUsuario) {
        return repository.findByTipo(tipoUsuario.toString()).stream().map(mapper::paraEntidade).toList();
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioModel model = repository.findByUuid(usuario.getUuid())
                .map(existente -> {
                    mapper.atualizarModelo(usuario, existente);
                    return existente;
                }).orElseGet(() -> mapper.paraModelo(usuario));

        UsuarioModel salvo = repository.save(model);
        return mapper.paraEntidade(salvo);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(Email email) {
        return repository.findByEmail(email.valor()).map(mapper::paraEntidade);
    }
}
