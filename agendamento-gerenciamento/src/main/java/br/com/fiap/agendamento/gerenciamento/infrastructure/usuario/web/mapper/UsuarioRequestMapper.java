package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.AdministradorCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.EnfermeiroCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.MedicoCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioRequestMapper {

    AdministradorCadastroDTO paraDTO(AdministradorRequest request);

    EnfermeiroCadastroDTO paraDTO(EnfermeiroRequest request);

    MedicoCadastroDTO paraDTO(MedicoRequest request);

    PacienteCadastroDTO paraDTO(PacienteRequest request);

    UsuarioResponse paraResponse(Usuario usuario);

    default String map(Email email) {
        return email != null ? email.valor() : null;
    }
}
