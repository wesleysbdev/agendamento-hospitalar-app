package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.web.controller;

import br.com.fiap.agendamento.notificacao.application.notificacao.dto.NotificacaoDTO;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.in.GestaoNotificacao;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.web.dto.NotificacaoRequest;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.web.mapper.NotificacaoMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notificacoes")
@AllArgsConstructor
public class NotificacaoController {

    private final GestaoNotificacao notificacaoService;
    private final NotificacaoMapper mapper;

    @PostMapping
    public void enviarNotificacao(@RequestBody @Valid NotificacaoRequest request) {
        NotificacaoDTO dto = mapper.paraDto(request);
        notificacaoService.enviarNotificacao(dto);
    }
}
