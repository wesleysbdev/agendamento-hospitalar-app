package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.UsuarioModel;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UsuarioModelMapper {

    Usuario paraEntidade(UsuarioModel model);

    Optional<Usuario> paraEntidade(Optional<UsuarioModel> model);

    <T extends UsuarioModel> T paraModelo(Usuario entidade);

    List<Usuario> paraEntidades(List<UsuarioModel> model);

    List<UsuarioModel> paraModelos(List<Usuario> entidade);

}
