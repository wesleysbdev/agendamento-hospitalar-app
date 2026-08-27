package br.com.fiap.agendamento.gerenciamento.infrastructure.autenticacao.adapter;

import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.GeradorTokenAutenticacao;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenAdapter implements GeradorTokenAutenticacao {

    private final JwtEncoder encoder;

    public TokenAdapter(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    @Value("${spring.security.jwt.issuer}")
    private String appName;

    @Value("${spring.security.jwt.expiration-seconds}")
    private long expirationSeconds;

    @Override
    public String gerar(Usuario usuario) {
        Instant agora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(appName)
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(expirationSeconds))
                .subject(usuario.getUuid().toString())
                .claim("role", usuario.getTipo().name())
                .claim("nome", usuario.getNome())
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
