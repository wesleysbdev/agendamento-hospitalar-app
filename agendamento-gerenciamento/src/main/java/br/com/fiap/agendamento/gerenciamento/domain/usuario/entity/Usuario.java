package br.com.fiap.agendamento.gerenciamento.domain.usuario.entity;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;

import java.util.UUID;

public abstract class Usuario {

    private final UUID uuid;
    private String nome;
    private Email email;
    private String senha;
    private boolean ativo;

    public Usuario(UUID uuid, String nome, Email email, String senha, boolean ativo) {
        validarDadosObrigatorios(uuid, nome, email, senha);
        this.uuid = uuid;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
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

    public void inativarUsuario() {
        this.ativo = false;
    }

    public void ativarUsuario() {
        this.ativo = true;
    }

    public void definirNovaSenha(String senhaHash) {
        if (nome == null || nome.isBlank()) {
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

    public abstract TipoUsuario getTipo();

    private static void validarDadosObrigatorios(UUID uuid, String nome, Email email, String senha) {
        if (uuid == null || nome == null || email == null || senha == null) {
            throw new UsuarioDadosInvalidosException("Nome, email e senha são dados obrigatórios.");
        }
    }
}
