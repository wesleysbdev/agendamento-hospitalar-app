package br.com.fiap.agendamento.gerenciamento.domain.usuario.entity;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;

import java.util.UUID;

public abstract sealed class Usuario permits Administrador, Medico, Paciente, Enfermeiro {

    private final UUID uuid;
    private String nome;
    private Email email;
    private String senha;
    private boolean ativo;
    private boolean excluido;

    public Usuario(UUID uuid, String nome, Email email, String senha, boolean ativo, boolean excluido) {
        validarDadosObrigatorios(uuid, nome, email, senha);
        this.uuid = uuid;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.excluido = excluido;
    }

    public void alterarDados(String nome, Email email) {
        if (nome == null || nome.isBlank()) {
            throw new UsuarioDadosInvalidosException("Nome é obrigatório.");
        }

        if (email == null) {
            throw new UsuarioDadosInvalidosException("Email é obrigatório.");
        }

        this.nome = nome;
        this.email = email;
    }

    public void inativar() {
        this.ativo = false;
    }

    public void ativar() {
        if (excluido) {
            throw new UsuarioDadosInvalidosException(
                    "Usuário excluído não pode ser ativado."
            );
        }
        this.ativo = true;
    }

    public void excluir() {
        this.excluido = true;
    }

    public void definirNovaSenha(String senhaHash) {
        if (senhaHash == null || senhaHash.isBlank()) {
            throw new UsuarioDadosInvalidosException("Nova senha é obrigatória.");
        }

        this.senha = senhaHash;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }

    public Email getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public abstract TipoUsuario getTipo();

    private static void validarDadosObrigatorios(UUID uuid, String nome, Email email, String senha) {
        if (uuid == null || uuid.toString().isBlank()) {
            throw new UsuarioDadosInvalidosException("UUID é obrigatório.");
        }

        if (nome == null || nome.isBlank()) {
            throw new UsuarioDadosInvalidosException("Nome é obrigatório.");
        }

        if (senha == null || senha.isBlank()) {
            throw new UsuarioDadosInvalidosException("A senha é obrigatória.");
        }
    }
}
