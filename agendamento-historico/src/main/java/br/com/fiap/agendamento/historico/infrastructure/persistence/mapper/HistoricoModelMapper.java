package br.com.fiap.agendamento.historico.infrastructure.persistence.mapper;

import br.com.fiap.agendamento.historico.domain.historico.entity.HistoricoConsulta;
import br.com.fiap.agendamento.historico.domain.historico.enums.EstadoConsulta;
import br.com.fiap.agendamento.historico.infrastructure.persistence.model.HistoricoConsultaModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoricoModelMapper {

    default EstadoConsulta paraEstadoConsulta(HistoricoConsultaModel.EstadoConsultaModel estadoModel) {
        return estadoModel == null ? null : EstadoConsulta.valueOf(estadoModel.name());
    }

    default HistoricoConsultaModel.EstadoConsultaModel paraEstadoConsultaModel(EstadoConsulta estado) {
        return estado == null ? null : HistoricoConsultaModel.EstadoConsultaModel.valueOf(estado.name());
    }

    HistoricoConsulta paraEntidade(HistoricoConsultaModel model);

    HistoricoConsultaModel paraModelo(HistoricoConsulta entidade);
}
