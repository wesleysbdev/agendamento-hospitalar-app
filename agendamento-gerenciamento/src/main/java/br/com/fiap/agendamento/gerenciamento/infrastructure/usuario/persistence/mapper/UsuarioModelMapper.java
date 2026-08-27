package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.*;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Crm;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UsuarioModelMapper {

    default Email paraEmail(String valor) {
        return valor == null ? null : new Email(valor);
    }

    default String paraString(Email email) {
        return email == null ? null : email.valor();
    }

    default Telefone paraTelefone(String valor) {
        return valor == null ? null : new Telefone(valor);
    }

    default String paraString(Telefone telefone) {
        return telefone == null ? null : telefone.valor();
    }

    default Crm paraCrm(String valor) {
        return valor == null ? null : Crm.criarDeTextoCompleto(valor);
    }

    default String paraString(Crm crm) {
        return crm == null ? null : crm.toString();
    }

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

    void atualizarModelo(Usuario usuario, @MappingTarget UsuarioModel existente);
}
