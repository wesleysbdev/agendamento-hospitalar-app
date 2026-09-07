package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.adapter;

import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.repository.ConsultaDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultaRepositoryAdapter implements ConsultaRepository {

    private final ConsultaDatasourceRepository consultaDatasourceRepository;

    @Override
    public Consulta salvar(Consulta consulta) {
        return null;
    }
}
