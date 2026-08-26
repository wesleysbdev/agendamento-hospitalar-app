package br.com.fiap.agendamento.gerenciamento.domain.usuario.vo;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;

import java.util.regex.Pattern;

public record Telefone(String valor) {

    public static final Pattern TELEFONE_PATTERN = Pattern.compile("\\d{10,11}$");

    public Telefone {
        if (valor == null || valor.isBlank() || !TELEFONE_PATTERN.matcher(valor).matches()) {
            throw new UsuarioDadosInvalidosException("Telefone em formato inválido.");
        }
    }
}
