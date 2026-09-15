package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.repository;

import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HospitalDatasourceRepository extends JpaRepository<HospitalModel, UUID> {
}
