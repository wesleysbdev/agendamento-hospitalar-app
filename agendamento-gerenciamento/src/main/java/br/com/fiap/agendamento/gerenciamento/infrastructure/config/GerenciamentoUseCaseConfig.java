package br.com.fiap.agendamento.gerenciamento.infrastructure.config;

import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoCadastroAgenda;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoConsultaAgenda;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.out.AgendaRepository;
import br.com.fiap.agendamento.gerenciamento.application.agenda.usecases.CadastroAgendaUseCase;
import br.com.fiap.agendamento.gerenciamento.application.agenda.usecases.ConsultaAgendaUseCase;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoCadastroConsulta;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoHistoricoConsulta;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaEventPublisher;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.application.consulta.usecases.CriarConsultaUseCase;
import br.com.fiap.agendamento.gerenciamento.application.consulta.usecases.ConsultaHistoricoUseCase;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoCadastroHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoConsultaHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoEditarHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.out.HospitalRepository;
import br.com.fiap.agendamento.gerenciamento.application.hospital.usecases.CadastroHospitalUseCase;
import br.com.fiap.agendamento.gerenciamento.application.hospital.usecases.ConsultaHospitalUseCase;
import br.com.fiap.agendamento.gerenciamento.application.hospital.usecases.EditarHospitalUseCase;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoAutenticacao;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoCadastroUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoConsultaUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoEditarUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.CodificadorSenha;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.GeradorTokenAutenticacao;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.application.usuario.usecases.AutenticacaoUseCase;
import br.com.fiap.agendamento.gerenciamento.application.usuario.usecases.CadastroUsuarioUseCase;
import br.com.fiap.agendamento.gerenciamento.application.usuario.usecases.ConsultaUsuarioUseCase;
import br.com.fiap.agendamento.gerenciamento.application.usuario.usecases.EditarUsuarioUseCase;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class GerenciamentoUseCaseConfig {

    @Bean
    public GestaoAutenticacao gestaoAutenticacao(UsuarioRepository repository, CodificadorSenha codificador, GeradorTokenAutenticacao tokenService) {
        return new AutenticacaoUseCase(repository, codificador, tokenService);
    }

    @Bean
    public GestaoCadastroUsuario gestaoCadastroUsuario(UsuarioRepository repository, CodificadorSenha codificadorSenha) {
        return new CadastroUsuarioUseCase(repository, codificadorSenha);
    }

    @Bean
    public GestaoConsultaUsuario gestaoConsultaUsuario(UsuarioRepository repository) {
        return new ConsultaUsuarioUseCase(repository);
    }

    @Bean
    public GestaoEditarUsuario gestaoEditarUsuario(UsuarioRepository repository, CodificadorSenha codificador) {
        return new EditarUsuarioUseCase(repository, codificador);
    }

    @Bean
    public GestaoCadastroConsulta criarConsultaUseCase(ConsultaEventPublisher consultaEventPublisher, ConsultaRepository repository, SecurityContextProvider securityContextProvider) {
        return new CriarConsultaUseCase(repository, consultaEventPublisher, securityContextProvider);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public GestaoHistoricoConsulta gestaoHistoricoConsulta(
            ConsultaRepository consultaRepository,
            UsuarioRepository usuarioRepository,
            Clock clock
    ) {
        return new ConsultaHistoricoUseCase(consultaRepository, usuarioRepository, clock);
    }

    @Bean
    public GestaoConsultaAgenda gestaoConsultaAgenda(AgendaRepository repository) {
        return new ConsultaAgendaUseCase(repository);
    }

    @Bean
    public GestaoCadastroAgenda gestaoCadastroAgenda(AgendaRepository repository, UsuarioRepository usuarioRepository, HospitalRepository hospitalRepository) {
        return new CadastroAgendaUseCase(repository, usuarioRepository, hospitalRepository);
    }

    @Bean
    public GestaoCadastroHospital gestaoCadastroHospital(HospitalRepository repository) {
        return new CadastroHospitalUseCase(repository);
    }

    @Bean
    public GestaoConsultaHospital gestaoConsultaHospital(HospitalRepository repository) {
        return new ConsultaHospitalUseCase(repository);
    }

    @Bean
    public GestaoEditarHospital gestaoEditarHospital(HospitalRepository repository) {
        return new EditarHospitalUseCase(repository);
    }
}
