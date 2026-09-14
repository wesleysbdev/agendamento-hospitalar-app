package br.com.fiap.agendamento.historico.infrastructure.persistence.adapter;

import br.com.fiap.agendamento.historico.application.historico.ports.out.HistoricoRepository;
import br.com.fiap.agendamento.historico.domain.historico.entity.HistoricoConsulta;
import br.com.fiap.agendamento.historico.infrastructure.persistence.mapper.HistoricoModelMapper;
import br.com.fiap.agendamento.historico.infrastructure.persistence.model.HistoricoConsultaModel;
import br.com.fiap.agendamento.historico.infrastructure.persistence.repository.HistoricoDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class HistoricoRepositoryAdapter implements HistoricoRepository {

    private final HistoricoDatasourceRepository repository;
    private final HistoricoModelMapper mapper;

    @Override
    public List<HistoricoConsulta> buscarPorPacienteUuid(UUID pacienteUuid) {
        List<HistoricoConsultaModel> models = repository.findByPacienteUuid(pacienteUuid);
        return models.stream()
                .map(mapper::paraEntidade)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoricoConsulta> buscarPorPacienteUuidEHorarioPosterior(UUID pacienteUuid, LocalDateTime horario) {
        List<HistoricoConsultaModel> models = repository.findByPacienteUuidAndHorarioAfter(pacienteUuid, horario);
        return models.stream()
                .map(mapper::paraEntidade)
                .collect(Collectors.toList());
    }
}
