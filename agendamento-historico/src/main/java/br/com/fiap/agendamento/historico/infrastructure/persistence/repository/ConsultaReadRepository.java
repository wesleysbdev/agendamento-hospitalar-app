package br.com.fiap.agendamento.historico.infrastructure.persistence.repository;

import br.com.fiap.agendamento.historico.infrastructure.persistence.model.ConsultaReadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultaReadRepository extends JpaRepository<ConsultaReadModel, UUID> {

    List<ConsultaReadModel> findByPacienteId(UUID pacienteId);
}
