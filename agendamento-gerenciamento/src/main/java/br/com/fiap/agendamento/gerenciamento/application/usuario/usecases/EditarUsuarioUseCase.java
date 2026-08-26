package br.com.fiap.agendamento.gerenciamento.application.usuario.usecases;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.*;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoEditarUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.CodificadorSenha;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.application.usuario.validator.PermissaoValidator;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.*;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoEncontradoException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Crm;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;

import java.util.UUID;

public class EditarUsuarioUseCase implements GestaoEditarUsuario {

    private final UsuarioRepository repository;
    private final CodificadorSenha codificador;

    public EditarUsuarioUseCase(UsuarioRepository repository, CodificadorSenha codificador) {
        this.repository = repository;
        this.codificador = codificador;
    }

    @Override
    public Usuario mudarEstadoDoUsuario(AlterarEstadoDTO alterarEstadoDTO, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.admin(usuarioAutenticado.tipo());
        Usuario usuario = buscarUsuarioPorUuid(alterarEstadoDTO.usuarioUuid());
        if (alterarEstadoDTO.ativo()) {
            usuario.ativar();
        } else {
            usuario.inativar();
        }
        return repository.salvar(usuario);
    }

    @Override
    public Usuario excluirUsuario(UUID usuarioUuid, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.admin(usuarioAutenticado.tipo());
        Usuario usuario = buscarUsuarioPorUuid(usuarioUuid);
        usuario.excluir();
        return repository.salvar(usuario);
    }

    @Override
    public Usuario alterarSenhaUsuario(AlteracaoSenhaDTO alteracaoSenha, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.apenasOProprio(usuarioAutenticado, alteracaoSenha.usuarioUuid());
        Usuario usuario = buscarUsuarioPorUuid(alteracaoSenha.usuarioUuid());
        usuario.definirNovaSenha(codificador.codificar(alteracaoSenha.senhaNova()));
        return repository.salvar(usuario);
    }

    @Override
    public Usuario alterarDadosAdministrador(AdministradorEdicaoDTO administradorEdicaoDTO, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.apenasOProprio(usuarioAutenticado, administradorEdicaoDTO.uuid());
        Administrador admin = buscarAdministradorPorUuid(administradorEdicaoDTO.uuid());
        validarEmailParaEdicao(admin, administradorEdicaoDTO.email());
        admin.alterarDados(administradorEdicaoDTO.nome(), new Email(administradorEdicaoDTO.email()));
        return repository.salvar(admin);
    }

    @Override
    public Usuario alterarDadosEnfermeiro(EnfermeiroEdicaoDTO enfermeiroEdicaoDTO, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.adminOuApenasProprio(usuarioAutenticado, enfermeiroEdicaoDTO.uuid());
        Enfermeiro enfermeiro = buscarEnfermeiroPorUuid(enfermeiroEdicaoDTO.uuid());
        validarEmailParaEdicao(enfermeiro, enfermeiroEdicaoDTO.email());
        enfermeiro.alterarDados(enfermeiroEdicaoDTO.nome(), new Email(enfermeiroEdicaoDTO.email()));
        return repository.salvar(enfermeiro);
    }

    @Override
    public Usuario alterarDadosPaciente(PacienteEdicaoDTO pacienteEdicaoDTO, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.adminEpacienteApenasProprio(usuarioAutenticado, pacienteEdicaoDTO.uuid());
        Paciente paciente = buscarPacientePorUuid(pacienteEdicaoDTO.uuid());
        validarEmailParaEdicao(paciente, pacienteEdicaoDTO.email());
        paciente.alterarDados(pacienteEdicaoDTO.nome(), new Email(pacienteEdicaoDTO.email()), new Telefone(pacienteEdicaoDTO.telefone()));
        return repository.salvar(paciente);
    }

    @Override
    public Usuario alterarDadosMedico(MedicoEdicaoDTO medicoEdicaoDTO, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.adminEMedicoApenasProprio(usuarioAutenticado, medicoEdicaoDTO.uuid());
        Medico medico = buscarMedicoPorUuid(medicoEdicaoDTO.uuid());
        validarEmailParaEdicao(medico, medicoEdicaoDTO.email());
        medico.alterarDados(medicoEdicaoDTO.nome(), new Email(medicoEdicaoDTO.email()), Crm.criarDeTextoCompleto(medicoEdicaoDTO.crm()));
        return repository.salvar(medico);
    }

    private void validarEmailParaEdicao(Usuario usuario, String emailStr) {
        if (!usuario.getEmail().valor().equalsIgnoreCase(emailStr) && repository.buscarPorEmail(new Email(emailStr)).isPresent()) {
            throw new UsuarioDadosInvalidosException("E-mail já cadastrado.");
        }
    }

    private Usuario buscarUsuarioPorUuid(UUID uuid) {
        return repository.buscarPorUuid(uuid).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
    }

    private Medico buscarMedicoPorUuid(UUID uuid) {
        Usuario usuario = buscarUsuarioPorUuid(uuid);

        if (usuario instanceof Medico medico) {
            return medico;
        }

        throw new UsuarioDadosInvalidosException("O usuário informado não é um médico.");
    }

    private Paciente buscarPacientePorUuid(UUID uuid) {
        Usuario usuario = buscarUsuarioPorUuid(uuid);

        if (usuario instanceof Paciente paciente) {
            return paciente;
        }

        throw new UsuarioDadosInvalidosException("O usuário informado não é um paciente.");
    }

    private Administrador buscarAdministradorPorUuid(UUID uuid) {
        Usuario usuario = buscarUsuarioPorUuid(uuid);

        if (usuario instanceof Administrador adm) {
            return adm;
        }

        throw new UsuarioDadosInvalidosException("O usuário informado não é um administrador.");
    }

    private Enfermeiro buscarEnfermeiroPorUuid(UUID uuid) {
        Usuario usuario = buscarUsuarioPorUuid(uuid);

        if (usuario instanceof Enfermeiro enfermeiro) {
            return enfermeiro;
        }

        throw new UsuarioDadosInvalidosException("O usuário informado não é um enfermeiro.");
    }

}
