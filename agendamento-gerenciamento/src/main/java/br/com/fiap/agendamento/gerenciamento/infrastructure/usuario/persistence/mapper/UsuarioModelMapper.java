package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.*;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.ValueObjectMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ValueObjectMapper.class)
public interface UsuarioModelMapper {

    default Usuario paraEntidade(UsuarioModel model) {
        return switch (model) {
            case AdministradorModel administrador -> paraEntidade(administrador);
            case EnfermeiroModel enfermeiro -> paraEntidade(enfermeiro);
            case MedicoModel medico -> paraEntidade(medico);
            case PacienteModel paciente -> paraEntidade(paciente);
            default -> throw new IllegalStateException("Valor de modelo inexperado: " + model);
        };
    }

    default UsuarioModel paraModelo(Usuario usuario) {
        return switch (usuario) {
            case Medico medico -> paraModelo(medico);
            case Paciente paciente -> paraModelo(paciente);
            case Administrador administrador -> paraModelo(administrador);
            case Enfermeiro enfermeiro -> paraModelo(enfermeiro);
        };
    }

    Administrador paraEntidade(AdministradorModel model);

    Enfermeiro paraEntidade(EnfermeiroModel model);

    Medico paraEntidade(MedicoModel model);

    Paciente paraEntidade(PacienteModel model);

    AdministradorModel paraModelo(Administrador entidade);

    EnfermeiroModel paraModelo(Enfermeiro entidade);

    MedicoModel paraModelo(Medico entidade);

    PacienteModel paraModelo(Paciente entidade);

    @Mapping(target = "id", ignore = true)
    void atualizarModelo(Usuario usuario, @MappingTarget UsuarioModel existente);
}
