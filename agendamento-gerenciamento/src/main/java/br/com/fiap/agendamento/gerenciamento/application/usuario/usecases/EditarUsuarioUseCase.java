package br.com.fiap.agendamento.gerenciamento.application.usuario.usecases;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.*;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoEditarUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.CodificadorSenha;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.application.usuario.validator.PermissaoValidator;
import br.com.fiap.agendamento.gerenciamento.application.usuario.validator.UsuarioValidator;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.*;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Crm;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;

public class EditarUsuarioUseCase implements GestaoEditarUsuario {

    private final UsuarioRepository repository;
    private final CodificadorSenha codificador;

    public EditarUsuarioUseCase(UsuarioRepository repository, CodificadorSenha codificador) {
        this.repository = repository;
        this.codificador = codificador;
    }

    @Override
    public void mudarEstadoDoUsuario(AlterarEstadoDTO alterarEstadoDTO, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.admin(usuarioAutenticado.tipo());
        Usuario usuario = UsuarioValidator.buscarUsuarioPorUuid(alterarEstadoDTO.usuarioUuid());
        if (alterarEstadoDTO.ativo()) {
            usuario.inativarUsuario();
        } else {
            usuario.ativarUsuario();
        }
        repository.salvar(usuario);
    }

    @Override
    public void alterarSenhaUsuario(AlteracaoSenhaDTO alteracaoSenha, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.apenasOProprio(usuarioAutenticado, alteracaoSenha.usuarioUuid());
        Usuario usuario = UsuarioValidator.buscarUsuarioPorUuid(alteracaoSenha.usuarioUuid());
        usuario.definirNovaSenha(codificador.codificar(alteracaoSenha.senhaNova()));
        repository.salvar(usuario);
    }

    @Override
    public void alterarDadosAdministrador(AdministradorEdicaoDTO administradorEdicaoDTO, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.apenasOProprio(usuarioAutenticado, administradorEdicaoDTO.uuid());
        Administrador admin = UsuarioValidator.buscarUsuarioPorUuid(administradorEdicaoDTO.uuid());
        UsuarioValidator.validarEmailParaEdicao(admin, administradorEdicaoDTO.email());
        admin.alterarDados(administradorEdicaoDTO.nome(), new Email(administradorEdicaoDTO.email()));
        repository.salvar(admin);
    }

    @Override
    public void alterarDadosEnfermeiro(EnfermeiroEdicaoDTO enfermeiroEdicaoDTO, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.adminOuApenasProprio(usuarioAutenticado, enfermeiroEdicaoDTO.uuid());
        Enfermeiro enfermeiro = UsuarioValidator.buscarUsuarioPorUuid(enfermeiroEdicaoDTO.uuid());
        UsuarioValidator.validarEmailParaEdicao(enfermeiro, enfermeiroEdicaoDTO.email());
        enfermeiro.alterarDados(enfermeiroEdicaoDTO.nome(), new Email(enfermeiroEdicaoDTO.email()));
        repository.salvar(enfermeiro);
    }

    @Override
    public void alterarDadosPaciente(PacienteEdicaoDTO pacienteEdicaoDTO, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.adminEpacienteApenasProprio(usuarioAutenticado, pacienteEdicaoDTO.uuid());
        Paciente paciente = UsuarioValidator.buscarUsuarioPorUuid(pacienteEdicaoDTO.uuid());
        UsuarioValidator.validarEmailParaEdicao(paciente, pacienteEdicaoDTO.email());
        paciente.alterarDados(pacienteEdicaoDTO.nome(), new Email(pacienteEdicaoDTO.email()), new Telefone(pacienteEdicaoDTO.telefone()));
        repository.salvar(paciente);
    }

    @Override
    public void alterarDadosMedico(MedicoEdicaoDTO medicoEdicaoDTO, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.adminEMedicoApenasProprio(usuarioAutenticado, medicoEdicaoDTO.uuid());
        Medico medico = UsuarioValidator.buscarUsuarioPorUuid(medicoEdicaoDTO.uuid());
        UsuarioValidator.validarEmailParaEdicao(medico, medicoEdicaoDTO.email());
        medico.alterarDados(medicoEdicaoDTO.nome(), new Email(medicoEdicaoDTO.email()), Crm.criarDeTextoCompleto(medicoEdicaoDTO.crm()));
        repository.salvar(medico);
    }

}
