package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.adapter;

import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.out.AgendaRepository;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.mapper.AgendaModelMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model.AgendaModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.repository.AgendaDatasourceRepository;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.repository.HospitalDatasourceRepository;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.MedicoModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.repository.UsuarioDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AgendaRepositoryAdapter implements AgendaRepository {

    private final AgendaDatasourceRepository repository;
    private final AgendaModelMapper mapper;
    private final UsuarioDatasourceRepository usuarioRepository;
    private final HospitalDatasourceRepository hospitalRepository;

    @Override
    public List<Agenda> listar() {
        return repository.findAllWithRelacionamentos()
                .stream()
                .map(mapper::paraEntidade)
                .toList();
    }

    @Override
    public Optional<Agenda> buscarPorUuid(UUID uuid) {
        return repository.findWithRelacionamentosById(uuid).map(mapper::paraEntidade);
    }

    @Override
    public List<Agenda> buscarPorMedico(UUID medicoUuid) {
        return repository.findByMedicoId(medicoUuid).stream().map(mapper::paraEntidade).toList();
    }

    @Override
    public List<Agenda> buscarPorHospital(UUID hospitalUuid) {
        return repository.findByHospitalId(hospitalUuid).stream().map(mapper::paraEntidade).toList();
    }

    @Override
    public Optional<Agenda> buscarPorMedicoEHospital(UUID medicoUuid, UUID hospitalUuid) {
        Optional<AgendaModel> model = repository.findByMedicoIdAndHospitalId(medicoUuid, hospitalUuid);
        return mapper.paraEntidade(model);
    }

    @Override
    public Agenda salvar(Agenda agenda) {

        AgendaModel model = repository
                .findWithRelacionamentosById(agenda.getId())
                .map(existente -> {
                    mapper.atualizarModelo(agenda, existente);
                    configurarRelacionamentos(agenda, existente);
                    return existente;
                })
                .orElseGet(() -> {
                    AgendaModel novo = mapper.paraModelo(agenda);
                    configurarRelacionamentos(agenda, novo);
                    return novo;
                });

        AgendaModel salvo = repository.save(model);

        return repository
                .findWithRelacionamentosById(salvo.getId())
                .map(mapper::paraEntidade)
                .orElseThrow(() ->
                        new IllegalStateException("Agenda salva não encontrada.")
                );
    }

    private void configurarRelacionamentos(Agenda agenda, AgendaModel model) {
        MedicoModel medico = usuarioRepository
                .findById(agenda.getMedico().getId())
                .filter(usuario -> usuario instanceof MedicoModel)
                .map(usuario -> (MedicoModel) usuario)
                .orElseThrow(() ->
                        new IllegalStateException("Médico da agenda não encontrado.")
                );

        HospitalModel hospital = hospitalRepository
                .findById(agenda.getHospital().getId())
                .orElseThrow(() ->
                        new IllegalStateException("Hospital da agenda não encontrado.")
                );

        model.setMedico(medico);
        model.setHospital(hospital);
    }
}
