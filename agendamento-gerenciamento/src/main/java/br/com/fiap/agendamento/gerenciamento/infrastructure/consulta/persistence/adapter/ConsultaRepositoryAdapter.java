package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.adapter;

import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.mapper.ConsultaModelMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.repository.ConsultaDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ConsultaRepositoryAdapter implements ConsultaRepository {

    private final ConsultaDatasourceRepository consultaDatasourceRepository;
    private final ConsultaModelMapper consultaModelMapper;

    @Override
    public Consulta salvar(Consulta consulta) {
        return null;
    }

    @Override
    public List<Consulta> buscarPorPaciente(UUID pacienteUuid) {
        return consultaDatasourceRepository.findHistoricoByPacienteUuid(pacienteUuid).stream()
                .map(consultaModelMapper::paraEntidade)
                .toList();
    }

    @Override
    public List<Consulta> buscarFuturasPorPaciente(UUID pacienteUuid, LocalDateTime agora) {
        return consultaDatasourceRepository.findFuturasByPacienteUuid(pacienteUuid, agora).stream()
                .map(consultaModelMapper::paraEntidade)
                .toList();
    }
}
