package br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Crm;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;
import org.springframework.stereotype.Component;

@Component
public class ValueObjectMapper {

    public Email paraEmail(String valor) {
        return valor == null ? null : new Email(valor);
    }

    public String paraString(Email email) {
        return email == null ? null : email.valor();
    }

    public Telefone paraTelefone(String valor) {
        return valor == null ? null : new Telefone(valor);
    }

    public String paraString(Telefone telefone) {
        return telefone == null ? null : telefone.valor();
    }

    public Crm paraCrm(String valor) {
        return valor == null ? null : Crm.criarDeTextoCompleto(valor);
    }

    public String paraString(Crm crm) {
        return crm == null ? null : crm.toString();
    }
}
