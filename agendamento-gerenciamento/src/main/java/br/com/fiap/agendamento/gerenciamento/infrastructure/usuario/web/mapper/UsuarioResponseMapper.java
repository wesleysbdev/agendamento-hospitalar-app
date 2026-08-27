package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta.UsuarioDTO;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.UsuarioResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioResponseMapper {

    UsuarioResponse paraResponse(UsuarioDTO usuario);

    default String map(Email email) {
        return email != null ? email.valor() : null;
    }
}
