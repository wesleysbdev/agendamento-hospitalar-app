package br.com.fiap.agendamento.gerenciamento.application.agenda.usecases;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.AgendaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoCadastroAgenda;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.out.AgendaRepository;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.out.HospitalRepository;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.exception.HospitalNaoEncontradoException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Medico;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoEncontradoException;

import java.util.UUID;

public class CadastroAgendaUseCase implements GestaoCadastroAgenda {

    private final AgendaRepository agendaRepository;
    private final UsuarioRepository usuarioRepository;
    private final HospitalRepository hospitalRepository;

    public CadastroAgendaUseCase(
            AgendaRepository agendaRepository,
            UsuarioRepository usuarioRepository,
            HospitalRepository hospitalRepository
    ) {
        this.agendaRepository = agendaRepository;
        this.usuarioRepository = usuarioRepository;
        this.hospitalRepository = hospitalRepository;
    }

    public Agenda cadastrar(AgendaCadastroDTO agendaCadastro) {
        Medico medico = usuarioRepository.buscarPorUuid(agendaCadastro.medicoUuid())
                .filter(usuario -> usuario instanceof Medico)
                .map(usuario -> (Medico) usuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Médico não encontrado"));

        Hospital hospital = hospitalRepository.buscarPorUuid(agendaCadastro.hospitalUuid())
                .orElseThrow(() -> new HospitalNaoEncontradoException("Hospital não encontrado"));

        Agenda agenda = new Agenda(
                UUID.randomUUID(),
                medico,
                hospital,
                agendaCadastro.horarios()
        );

        return agendaRepository.salvar(agenda);
    }
}
