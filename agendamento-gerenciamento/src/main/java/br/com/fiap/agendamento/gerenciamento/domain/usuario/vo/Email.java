package br.com.fiap.agendamento.gerenciamento.domain.usuario.vo;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;

import java.util.regex.Pattern;

public record Email(String valor) {

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public Email {
        if (valor == null || valor.isBlank() || !EMAIL_PATTERN.matcher(valor).matches()) {
            throw new UsuarioDadosInvalidosException("E-mail em formato inválido.");
        }
        valor = valor.toLowerCase();
    }
}
