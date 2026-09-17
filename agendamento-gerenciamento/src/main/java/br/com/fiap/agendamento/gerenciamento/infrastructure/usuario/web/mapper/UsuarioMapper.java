package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.AdministradorCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.EnfermeiroCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.MedicoCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta.*;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.AdministradorEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.EnfermeiroEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.MedicoEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.PacienteEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.*;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.ValueObjectMapper;
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
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ValueObjectMapper.class)
public interface UsuarioMapper {

    default UsuarioResponse paraResponse(Usuario usuario) {
        return switch (usuario) {
            case Administrador administrador -> paraResponse(administrador);
            case Medico medico -> paraResponse(medico);
            case Paciente paciente -> paraResponse(paciente);
            case Enfermeiro enfermeiro -> paraResponse(enfermeiro);
        };
    }

    default UsuarioResponse paraResponse(UsuarioDTO usuarioDTO) {
        return switch (usuarioDTO) {
            case AdministradorDTO administrador -> paraResponse(administrador);
            case MedicoDTO medico -> paraResponse(medico);
            case PacienteDTO paciente -> paraResponse(paciente);
            case EnfermeiroDTO enfermeiro -> paraResponse(enfermeiro);
            default -> throw new IllegalStateException("Usuario incompatível para o mapeamento: " + usuarioDTO);
        };
    }

    UsuarioResponse paraResponse(Administrador administrador);

    UsuarioResponse paraResponse(Medico medico);

    UsuarioResponse paraResponse(Paciente paciente);

    UsuarioResponse paraResponse(Enfermeiro enfermeiro);

    UsuarioResponse paraResponse(AdministradorDTO usuarioDto);

    UsuarioResponse paraResponse(MedicoDTO usuarioDto);

    UsuarioResponse paraResponse(EnfermeiroDTO usuarioDto);

    UsuarioResponse paraResponse(PacienteDTO usuarioDto);

    AdministradorCadastroDTO paraDTO(AdministradorCadastroRequest request);

    EnfermeiroCadastroDTO paraDTO(EnfermeiroCadastroRequest request);

    MedicoCadastroDTO paraDTO(MedicoCadastroRequest request);

    PacienteCadastroDTO paraDTO(PacienteCadastroRequest request);

    AdministradorEdicaoDTO paraDTO(AdministradorEdicaoRequest request);

    EnfermeiroEdicaoDTO paraDTO(EnfermeiroEdicaoRequest request);

    MedicoEdicaoDTO paraDTO(MedicoEdicaoRequest request);

    PacienteEdicaoDTO paraDTO(PacienteEdicaoRequest request);

}
