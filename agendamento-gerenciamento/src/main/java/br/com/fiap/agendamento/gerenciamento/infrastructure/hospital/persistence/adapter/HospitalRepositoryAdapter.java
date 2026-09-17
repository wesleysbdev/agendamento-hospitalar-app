package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.adapter;

import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.out.HospitalRepository;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.mapper.HospitalModelMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.repository.HospitalDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class HospitalRepositoryAdapter implements HospitalRepository {

    private final HospitalDatasourceRepository repository;
    private final HospitalModelMapper mapper;

    @Override
    public List<Hospital> listar() {
        return repository.findAll().stream().map(mapper::paraEntidade).toList();
    }

    @Override
    public Optional<Hospital> buscarPorId(UUID uuid) {
        return repository.findById(uuid).map(mapper::paraEntidade);
    }

    @Override
    public Hospital salvar(Hospital hospital) {
        HospitalModel model = repository.findById(hospital.getId())
                .map(existente -> {
                    mapper.atualizarModelo(hospital, existente);
                    return existente;
                }).orElseGet(() -> mapper.paraModelo(hospital));

        HospitalModel salvo = repository.save(model);
        return mapper.paraEntidade(salvo);
    }
}
