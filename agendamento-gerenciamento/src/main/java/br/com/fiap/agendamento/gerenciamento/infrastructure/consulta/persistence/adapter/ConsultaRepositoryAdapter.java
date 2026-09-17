package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.adapter;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaDTO;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.enums.StatusConsulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model.AgendaModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.repository.AgendaDatasourceRepository;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.mapper.ConsultaModelMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model.ConsultaModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.repository.ConsultaDatasourceRepository;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.MedicoModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.PacienteModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.repository.UsuarioDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ConsultaRepositoryAdapter implements ConsultaRepository {

    private final ConsultaDatasourceRepository repository;
    private final ConsultaModelMapper mapper;
    private final UsuarioDatasourceRepository usuarioRepository;
    private final AgendaDatasourceRepository agendaRepository;

    @Override
    public Consulta salvar(Consulta consulta) {

        ConsultaModel model = repository
                .findById(consulta.getId())
                .map(existente -> {
                    mapper.atualizarModelo(consulta, existente);
                    configurarRelacionamentos(consulta, existente);
                    return existente;
                })
                .orElseGet(() -> {
                    ConsultaModel novo = mapper.paraModelo(consulta);
                    configurarRelacionamentos(consulta, novo);
                    return novo;
                });

        ConsultaModel salvo = repository.save(model);

        return repository
                .findWithRelacionamentosById(salvo.getId())
                .map(mapper::paraEntidade)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Consulta salva não foi encontrada."
                        )
                );
    }

    @Override
    public Optional<Consulta> buscarPorId(UUID uuid) {
        return repository
                .findWithRelacionamentosById(uuid)
                .map(mapper::paraEntidade);
    }

    @Override
    public List<ConsultaDTO> buscarTodos() {
        return repository.findAllWithRelacionamentos()
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    @Override
    public boolean existeConsulta(
            UUID agendaId,
            LocalDate data,
            LocalTime hora
    ) {
        return repository.existeConsulta(
                agendaId,
                data,
                hora,
                List.of(
                        StatusConsulta.AGENDADA,
                        StatusConsulta.CONFIRMADA
                )
        );
    }

    @Override
    public List<ConsultaDTO> buscarPorPacienteId(UUID id) {
        return repository.findByPacienteId(id)
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    @Override
    public List<ConsultaDTO> buscarPorMedicoId(UUID id) {
        return repository.findByMedicoId(id)
                .stream()
                .map(this::paraDTO)
                .toList();
    }

    private void configurarRelacionamentos(
            Consulta consulta,
            ConsultaModel model
    ) {
        PacienteModel paciente = buscarPaciente(consulta.getPaciente().getId());
        AgendaModel agenda = buscarAgenda(consulta.getAgenda().getId());

        model.setPaciente(paciente);
        model.setAgenda(agenda);
    }

    private PacienteModel buscarPaciente(UUID pacienteId) {
        return usuarioRepository
                .findById(pacienteId)
                .filter(usuario -> usuario instanceof PacienteModel)
                .map(usuario -> (PacienteModel) usuario)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Paciente da consulta não encontrado."
                        )
                );
    }

    private AgendaModel buscarAgenda(UUID agendaId) {
        return agendaRepository
                .findById(agendaId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Agenda da consulta não encontrada."
                        )
                );
    }

    private ConsultaDTO paraDTO(ConsultaModel model) {

        AgendaModel agenda = model.getAgenda();
        MedicoModel medico = agenda.getMedico();
        HospitalModel hospital = agenda.getHospital();

        return new ConsultaDTO(
                model.getId(),
                LocalDateTime.of(
                        model.getData(),
                        model.getHorario()
                ),
                medico.getNome(),
                hospital.getNome(),
                hospital.getEndereco(),
                model.getStatus()
        );
    }
}