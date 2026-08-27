package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.repository;

import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioDatasourceRepository extends JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> findByUuid(UUID uuid);

    @Query(value = "SELECT * FROM usuarios WHERE tipo = :tipoUsuario", nativeQuery = true)
    List<UsuarioModel> findByTipo(String tipoUsuario);

    Optional<UsuarioModel> findByEmail(String valor);
}
