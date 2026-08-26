package br.com.fiap.agendamento.gerenciamento.infrastructure.autenticacao.adapter;

import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.CodificadorSenha;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CodificadorDeSenhaAdapter implements CodificadorSenha {

    private final PasswordEncoder encoder;

    @Override
    public String codificar(String senhaEmTexto) {
        return encoder.encode(senhaEmTexto);
    }

    @Override
    public boolean correspondente(String senhaHash, String senhaEmTexto) {
        return encoder.matches(senhaEmTexto, senhaHash);
    }
}
