package br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out;


import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaEvent;

public interface ConsultaEventPublisher {

    void publicar(ConsultaEvent event);

    void publicarAtualizacao(ConsultaEvent event);
}
