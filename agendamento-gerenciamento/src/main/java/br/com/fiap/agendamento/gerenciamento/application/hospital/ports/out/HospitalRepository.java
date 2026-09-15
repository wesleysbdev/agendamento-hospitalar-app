package br.com.fiap.agendamento.gerenciamento.application.hospital.ports.out;

import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HospitalRepository {
    List<Hospital> listar();

    Optional<Hospital> buscarPorId(UUID uuid);

    Hospital salvar(Hospital hospital);
}
