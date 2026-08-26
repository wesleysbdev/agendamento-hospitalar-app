package br.com.fiap.agendamento.gerenciamento.infrastructure.autenticacao.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.autenticacao.AutenticacaoDTO;
import br.com.fiap.agendamento.gerenciamento.infrastructure.autenticacao.web.dto.AutenticacaoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutenticacaoMapper {
    AutenticacaoDTO paraDto(AutenticacaoRequest request);
}
