package br.com.fiap.agendamento.gerenciamento.application.usuario.usecases;

import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.autenticacao.AutenticacaoDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoAutenticacao;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.CodificadorSenha;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.GeradorTokenAutenticacao;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.CredenciaisInvalidasException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;

public class AutenticacaoUseCase implements GestaoAutenticacao {

    private final UsuarioRepository repository;
    private final CodificadorSenha codificador;
    private final GeradorTokenAutenticacao tokenService;

    public AutenticacaoUseCase(UsuarioRepository repository, CodificadorSenha codificador, GeradorTokenAutenticacao tokenService) {
        this.repository = repository;
        this.codificador = codificador;
        this.tokenService = tokenService;
    }

    @Override
    public String login(AutenticacaoDTO autenticacao) {

        Usuario usuario = repository.buscarPorEmail(new Email(autenticacao.email()))
                .orElseThrow(CredenciaisInvalidasException::new);

        if (usuario.isExcluido() || !usuario.isAtivo()) {
            throw new CredenciaisInvalidasException();
        }

        if (!codificador.correspondente((usuario.getSenha()), autenticacao.senha())) {
            throw new CredenciaisInvalidasException();
        }

        return tokenService.gerar(usuario);
    }
}
