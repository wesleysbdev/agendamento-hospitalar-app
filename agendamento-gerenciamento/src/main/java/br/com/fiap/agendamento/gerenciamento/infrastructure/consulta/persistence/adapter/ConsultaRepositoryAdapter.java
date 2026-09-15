package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.adapter;

import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.mapper.ConsultaModelMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model.ConsultaModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.repository.ConsultaDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ConsultaRepositoryAdapter implements ConsultaRepository {

    private final ConsultaDatasourceRepository repository;
    private final ConsultaModelMapper mapper;

    @Override
    public Consulta salvar(Consulta consulta) {
        ConsultaModel model = repository.findById(consulta.getId())
                .map(existente -> {
                    mapper.atualizarModelo(consulta, existente);
                    return existente;
                }).orElseGet(() -> mapper.paraModelo(consulta));

        ConsultaModel salvo = repository.save(model);
        return mapper.paraEntidade(salvo);
    }

    @Override
    public Optional<Consulta> buscarPorId(UUID uuid) {
        return repository.findById(uuid).map(mapper::paraEntidade);
    }

    @Override
    public List<Consulta> buscarTodos() {
        return repository.findAll().stream().map(mapper::paraEntidade).toList();
    }

    @Override
    public boolean existeConsulta(UUID agendaId, LocalDate data, LocalTime hora) {
        return repository.existeConsulta(agendaId, data, hora);
    }

    @Override
    public List<Consulta> buscarPorPacienteId(UUID id) {
        return repository.findByPacienteId(id).stream().map(mapper::paraEntidade).toList();
    }

    @Override
    public List<Consulta> buscarPorMedicoId(UUID id) {
        return repository.findByMedicoId(id).stream().map(mapper::paraEntidade).toList();
    }
}
