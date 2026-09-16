package br.com.fiap.agendamento.historico.infrastructure.config;

import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultaPorIdUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasFuturasUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarConsultasPassadasUseCase;
import br.com.fiap.agendamento.historico.application.consulta.ports.in.BuscarHistoricoPacienteUseCase;
import br.com.fiap.agendamento.historico.application.consulta.usecases.BuscarConsultaPorIdUseCaseImpl;
import br.com.fiap.agendamento.historico.application.consulta.usecases.BuscarConsultasFuturasUseCaseImpl;
import br.com.fiap.agendamento.historico.application.consulta.usecases.BuscarConsultasPassadasUseCaseImpl;
import br.com.fiap.agendamento.historico.application.consulta.usecases.BuscarHistoricoPacienteUseCaseImpl;
import br.com.fiap.agendamento.historico.domain.consulta.ports.out.ConsultaHistoricoRepositoryPort;
import br.com.fiap.agendamento.historico.infrastructure.persistence.adapter.ConsultaHistoricoRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsultaHistoricoConfiguration {

    @Bean
    public BuscarHistoricoPacienteUseCase buscarHistoricoPacienteUseCase(ConsultaHistoricoRepositoryPort repositoryPort) {
        return new BuscarHistoricoPacienteUseCaseImpl(repositoryPort);
    }

    @Bean
    public BuscarConsultasFuturasUseCase buscarConsultasFuturasUseCase(ConsultaHistoricoRepositoryPort repositoryPort) {
        return new BuscarConsultasFuturasUseCaseImpl(repositoryPort);
    }

    @Bean
    public BuscarConsultasPassadasUseCase buscarConsultasPassadasUseCase(ConsultaHistoricoRepositoryPort repositoryPort) {
        return new BuscarConsultasPassadasUseCaseImpl(repositoryPort);
    }

    @Bean
    public BuscarConsultaPorIdUseCase buscarConsultaPorIdUseCase(ConsultaHistoricoRepositoryPort repositoryPort) {
        return new BuscarConsultaPorIdUseCaseImpl(repositoryPort);
    }
}
