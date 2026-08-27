package br.com.fiap.agendamento.gerenciamento.infrastructure.config;

import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoAutenticacao;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoCadastroUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.CodificadorSenha;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.GeradorTokenAutenticacao;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.application.usuario.usecases.AutenticacaoUseCase;
import br.com.fiap.agendamento.gerenciamento.application.usuario.usecases.CadastroUsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public GestaoCadastroUsuario gestaoCadastroUsuario(UsuarioRepository repository, CodificadorSenha codificadorSenha) {
        return new CadastroUsuarioUseCase(repository, codificadorSenha);
    }

    @Bean
    public GestaoAutenticacao gestaoAutenticacao(UsuarioRepository repository, CodificadorSenha codificador, GeradorTokenAutenticacao tokenService) {
        return new AutenticacaoUseCase(repository, codificador, tokenService);
    }
}
