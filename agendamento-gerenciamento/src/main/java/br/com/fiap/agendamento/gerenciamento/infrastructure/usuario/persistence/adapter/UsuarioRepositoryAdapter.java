package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.adapter;

import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.*;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.mapper.UsuarioModelMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.MedicoModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.PacienteModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.UsuarioModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.repository.UsuarioDatasourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioDatasourceRepository repository;
    private final UsuarioModelMapper mapper;

    @Override
    public List<Usuario> listar() {
        List<UsuarioModel> model = repository.findAll();
        return mapper.paraEntidades(model);
    }

    @Override
    public <T extends Usuario> Optional<T> buscarPorUuid(UUID uuid) {
        Optional<UsuarioModel> model = repository.findByUuid(uuid);
        return (Optional<T>) mapper.paraEntidade(model);
    }

    @Override
    public List<Usuario> listarPorTipo(TipoUsuario tipoUsuario) {
        return mapper.paraEntidades(repository.findByTipo(tipoUsuario));
    }

    @Override
    public void salvar(Usuario usuario) {

        UsuarioModel model = switch (usuario) {
            case Medico medico -> {
                MedicoModel medicoModel = mapper.paraModelo(usuario);
                medicoModel.setCrm(medico.getCrm().toString());
                yield medicoModel;
            }
            case Paciente paciente -> {
                PacienteModel pacienteModel = mapper.paraModelo(usuario);
                pacienteModel.setTelefone(paciente.getTelefone().valor());
                yield pacienteModel;
            }
            case Administrador administrador -> mapper.paraModelo(usuario);
            case Enfermeiro enfermeiro -> mapper.paraModelo(usuario);
            default -> throw new IllegalArgumentException("Tipo de usuário não suportado para persistência: " + usuario.getClass().getName());
        };

        repository.save(model);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(Email email) {
        return mapper.paraEntidade(repository.findByEmail(email.valor()));
    }
}
