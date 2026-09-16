package br.com.fiap.agendamento.historico.infrastructure.persistence.adapter;

import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.domain.consulta.ports.out.ConsultaHistoricoRepositoryPort;
import br.com.fiap.agendamento.historico.infrastructure.persistence.mapper.ConsultaHistoricoModelMapper;
import br.com.fiap.agendamento.historico.infrastructure.persistence.model.ConsultaHistoricoModel;
import br.com.fiap.agendamento.historico.infrastructure.persistence.repository.ConsultaHistoricoDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ConsultaHistoricoRepositoryAdapter implements ConsultaHistoricoRepositoryPort {

    private final ConsultaHistoricoDatasourceRepository repository;
    private final ConsultaHistoricoModelMapper mapper;

    @Override
    public Optional<ConsultaHistorico> buscarPorId(UUID id) {
        return repository.findById(id).map(mapper::paraEntidade);
    }

    @Override
    public List<ConsultaHistorico> buscarPorPacienteId(UUID pacienteId) {
        return repository.findByPacienteId(pacienteId).stream().map(mapper::paraEntidade).toList();
    }

    @Override
    public List<ConsultaHistorico> buscarConsultasFuturasPorPaciente(UUID pacienteId) {
        return repository.findFutureByPacienteId(pacienteId, LocalDate.now()).stream()
                .map(mapper::paraEntidade).toList();
    }

    @Override
    public List<ConsultaHistorico> buscarConsultasPassadasPorPaciente(UUID pacienteId) {
        return repository.findPastByPacienteId(pacienteId, LocalDate.now(), LocalTime.now()).stream()
                .map(mapper::paraEntidade).toList();
    }
}
