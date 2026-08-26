package br.com.fiap.agendamento.gerenciamento.application.usuario.usecases;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.validator.PermissaoValidator;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.AdministradorCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.EnfermeiroCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.MedicoCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoCadastroUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.CodificadorSenha;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.*;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Crm;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;

import java.util.UUID;

public class CadastroUsuarioUseCase implements GestaoCadastroUsuario {

    private final UsuarioRepository repository;
    private final CodificadorSenha codificador;

    public CadastroUsuarioUseCase(UsuarioRepository repository, CodificadorSenha codificador) {
        this.repository = repository;
        this.codificador = codificador;
    }

    @Override
    public Usuario cadastrarPaciente(PacienteCadastroDTO pacienteCadastro) {
        validarEmailParaCriacao(pacienteCadastro.email());
        Paciente paciente = new Paciente(
                UUID.randomUUID(),
                pacienteCadastro.nome(),
                new Email(pacienteCadastro.email()),
                new Telefone(pacienteCadastro.telefone()),
                codificador.codificar(pacienteCadastro.senha()),
                true,
                false
        );

        return this.repository.salvar(paciente);
    }

    @Override
    public Usuario cadastrarAdministrador(AdministradorCadastroDTO usuarioCadastro, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.admin(usuarioAutenticado.tipo());
        validarEmailParaCriacao(usuarioCadastro.email());
        Administrador administrador = new Administrador(
                UUID.randomUUID(),
                usuarioCadastro.nome(),
                new Email(usuarioCadastro.email()),
                codificador.codificar(usuarioCadastro.senha()),
                true,
                false
        );

        return this.repository.salvar(administrador);
    }

    @Override
    public Usuario cadastrarEnfermeiro(EnfermeiroCadastroDTO enfermeiroCadastro, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.adminEEnfermeiro(usuarioAutenticado.tipo());
        validarEmailParaCriacao(enfermeiroCadastro.email());
        Enfermeiro enfermeiro = new Enfermeiro(
                UUID.randomUUID(),
                enfermeiroCadastro.nome(),
                new Email(enfermeiroCadastro.email()),
                codificador.codificar(enfermeiroCadastro.senha()),
                true,
                false
        );

        return this.repository.salvar(enfermeiro);
    }

    @Override
    public Usuario cadastrarMedico(MedicoCadastroDTO medicoCadastro, UsuarioAutenticado usuarioAutenticado) {
        PermissaoValidator.adminEEnfermeiro(usuarioAutenticado.tipo());
        validarEmailParaCriacao(medicoCadastro.email());
        Medico medico = new Medico(
                UUID.randomUUID(),
                medicoCadastro.nome(),
                new Email(medicoCadastro.email()),
                codificador.codificar(medicoCadastro.senha()),
                true,
                Crm.criarDeTextoCompleto(medicoCadastro.crm()),
                false
        );

        return repository.salvar(medico);
    }

    private void validarEmailParaCriacao(String emailStr) {
        if (repository.buscarPorEmail(new Email(emailStr)).isPresent()) {
            throw new UsuarioDadosInvalidosException("E-mail já cadastrado.");
        }
    }

}
