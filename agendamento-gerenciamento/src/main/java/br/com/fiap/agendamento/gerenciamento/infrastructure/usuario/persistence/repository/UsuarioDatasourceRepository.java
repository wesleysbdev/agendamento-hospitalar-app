package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.repository;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioDatasourceRepository extends JpaRepository<UsuarioModel, Long> {
    <T extends UsuarioModel> Optional<T> findByUuid(UUID uuid);

    List<UsuarioModel> findByTipo(TipoUsuario tipoUsuario);

    Optional<UsuarioModel> findByEmail(String valor);
}
