package br.com.fiap.agendamento.gerenciamento.infrastructure.config.security;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityContextProvider {

    public UsuarioAutenticado obterUsuarioAutenticado() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Nenhum usuário autenticado encontrado no contexto.");
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID uuid = UUID.fromString(jwt.getSubject());
        String nome = jwt.getClaimAsString("nome");

        String roleStr = jwt.getClaimAsString("role").replace("ROLE_", "");
        TipoUsuario tipo = TipoUsuario.valueOf(roleStr);

        return new UsuarioAutenticado(uuid, nome, tipo);
    }

}
