package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.repository;

import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model.ConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaDatasourceRepository extends JpaRepository<ConsultaModel, Long> {
}
