package br.com.fiap.agendamento.historico.infrastructure.persistence.adapter;

import br.com.fiap.agendamento.historico.application.consulta.ports.out.ConsultaReadPort;
import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.infrastructure.persistence.mapper.ConsultaHistoricoMapper;
import br.com.fiap.agendamento.historico.infrastructure.persistence.model.ConsultaReadModel;
import br.com.fiap.agendamento.historico.infrastructure.persistence.repository.ConsultaReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultaReadRepositoryAdapter implements ConsultaReadPort {

    private final ConsultaReadRepository repository;
    private final ConsultaHistoricoMapper mapper;

    @Override
    public List<ConsultaHistorico> buscarPorPacienteId(java.util.UUID pacienteId) {
        return repository.findByPacienteId(pacienteId)
                .stream()
                .map(mapper::paraHistorico)
                .toList();
    }

    @Override
    public List<ConsultaHistorico> buscarConsultasFuturasPorPacienteId(
            java.util.UUID pacienteId,
            LocalDate dataAtual,
            LocalTime horarioAtual
    ) {
        List<ConsultaReadModel> todasConsultas = repository.findByPacienteId(pacienteId);
        
        return todasConsultas.stream()
                .filter(consulta -> isFutura(consulta, dataAtual, horarioAtual))
                .map(mapper::paraHistorico)
                .toList();
    }

    private boolean isFutura(ConsultaReadModel consulta, LocalDate dataAtual, LocalTime horarioAtual) {
        LocalDate dataConsulta = consulta.getData();
        LocalTime horarioConsulta = consulta.getHorario();
        
        if (dataConsulta.isAfter(dataAtual)) {
            return true;
        }
        
        if (dataConsulta.isEqual(dataAtual) && horarioConsulta.isAfter(horarioAtual)) {
            return true;
        }
        
        return false;
    }
}
