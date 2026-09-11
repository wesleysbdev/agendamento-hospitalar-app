package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.AdministradorCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.EnfermeiroCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.MedicoCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta.UsuarioDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.AdministradorEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.EnfermeiroEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.MedicoEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.PacienteEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.UsuarioResponse;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.cadastro.AdministradorCadastroRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.cadastro.EnfermeiroCadastroRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.cadastro.MedicoCadastroRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.cadastro.PacienteCadastroRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.edicao.AdministradorEdicaoRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.edicao.EnfermeiroEdicaoRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.edicao.MedicoEdicaoRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.edicao.PacienteEdicaoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    AdministradorCadastroDTO paraDTO(AdministradorCadastroRequest request);

    EnfermeiroCadastroDTO paraDTO(EnfermeiroCadastroRequest request);

    MedicoCadastroDTO paraDTO(MedicoCadastroRequest request);

    PacienteCadastroDTO paraDTO(PacienteCadastroRequest request);

    AdministradorEdicaoDTO paraDTO(AdministradorEdicaoRequest request);

    EnfermeiroEdicaoDTO paraDTO(EnfermeiroEdicaoRequest request);

    MedicoEdicaoDTO paraDTO(MedicoEdicaoRequest request);

    PacienteEdicaoDTO paraDTO(PacienteEdicaoRequest request);

    UsuarioResponse paraResponse(Usuario usuario);

    UsuarioResponse paraResponse(UsuarioDTO usuarioDto);

    default String map(Email email) {
        return email != null ? email.valor() : null;
    }
}
