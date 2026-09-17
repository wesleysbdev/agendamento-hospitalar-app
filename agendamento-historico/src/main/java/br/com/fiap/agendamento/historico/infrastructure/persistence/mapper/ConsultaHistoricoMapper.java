package br.com.fiap.agendamento.historico.infrastructure.persistence.mapper;

import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.infrastructure.persistence.model.ConsultaReadModel;
import org.springframework.stereotype.Component;

@Component
public class ConsultaHistoricoMapper {

    public ConsultaHistorico paraHistorico(ConsultaReadModel model) {
        return new ConsultaHistorico(
                model.getId(),
                model.getPacienteId(),
                model.getAgendaId(),
                model.getData(),
                model.getHorario(),
                model.getStatus(),
                model.getCriadoEm()
        );
    }
}
