package br.com.fiap.agendamento.gerenciamento.infrastructure.autenticacao.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.autenticacao.AutenticacaoDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoAutenticacao;
import br.com.fiap.agendamento.gerenciamento.infrastructure.autenticacao.web.dto.AutenticacaoRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.autenticacao.web.dto.AutenticacaoResponse;
import br.com.fiap.agendamento.gerenciamento.infrastructure.autenticacao.web.mapper.AutenticacaoMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("autenticacao")
@AllArgsConstructor
public class AutenticacaoController {

    private final GestaoAutenticacao autenticacaoService;
    private final AutenticacaoMapper mapper;

    @PostMapping
    public AutenticacaoResponse autenticar(@RequestBody @Valid AutenticacaoRequest request) {
        AutenticacaoDTO dto = mapper.paraDto(request);
        var token = autenticacaoService.login(dto);
        return new AutenticacaoResponse(token);
    }

}
