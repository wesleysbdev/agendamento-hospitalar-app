package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.PacienteRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioRequestMapper {

    PacienteCadastroDTO toDTO(PacienteRequest request);
}
