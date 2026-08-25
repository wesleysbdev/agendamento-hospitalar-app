package br.com.fiap.agendamento.gerenciamento.application.usuario.validator;

import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoEncontradoException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;

import java.util.UUID;

public abstract class UsuarioValidator {

    private static UsuarioRepository repository;

    public UsuarioValidator(UsuarioRepository repository) {
        UsuarioValidator.repository = repository;
    }

    public static void validarEmailParaCriacao(String emailStr) {
        if (repository.buscarPorEmail(new Email(emailStr)).isPresent()) {
            throw new UsuarioDadosInvalidosException("E-mail já cadastrado.");
        }
    }

    public static void validarEmailParaEdicao(Usuario usuario, String emailStr) {
        if (!usuario.getEmail().valor().equalsIgnoreCase(emailStr)) {
            validarEmailParaCriacao(emailStr);
        }
    }

    public static <T extends Usuario> T buscarUsuarioPorUuid(UUID uuid) {
        return (T) repository.buscarPorUuid(uuid).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
    }
}
