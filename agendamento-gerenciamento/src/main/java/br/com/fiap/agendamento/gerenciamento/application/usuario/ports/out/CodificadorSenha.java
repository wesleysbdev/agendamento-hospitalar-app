package br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out;

public interface CodificadorSenha {
    String codificar(String senhaEmTexto);
    boolean correspondente(String senhaHash, String senhaEmTexto);
}
