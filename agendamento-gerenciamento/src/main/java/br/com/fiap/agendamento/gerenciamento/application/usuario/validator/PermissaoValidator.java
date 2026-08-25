package br.com.fiap.agendamento.gerenciamento.application.usuario.validator;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoAutorizadoException;

import java.util.Set;
import java.util.UUID;

public abstract class PermissaoValidator {

    public static void verificaPermissao(TipoUsuario tipoUsuario, Set<TipoUsuario> tiposPermitidos) {
        if (!tiposPermitidos.contains(tipoUsuario)) {
            throw new UsuarioNaoAutorizadoException();
        }
    }

    public static void apenasOProprio(UsuarioAutenticado usuarioAutenticado, UUID alvoUuid) {
        if (usuarioAutenticado.tipo() != TipoUsuario.ADMINISTRADOR && usuarioAutenticado.uuid() != alvoUuid) {
            throw new UsuarioNaoAutorizadoException();
        }
    }

    public static void admin(TipoUsuario tipoUsuario) {
        verificaPermissao(tipoUsuario, Set.of(TipoUsuario.ADMINISTRADOR));
    }

    public static void adminEMedicoApenasProprio(UsuarioAutenticado usuarioAutenticado, UUID alvoUuid) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.MEDICO));
        apenasOProprio(usuarioAutenticado, alvoUuid);
    }

    public static void adminEpacienteApenasProprio(UsuarioAutenticado usuarioAutenticado, UUID alvoUuid) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.PACIENTE));
        apenasOProprio(usuarioAutenticado, alvoUuid);
    }

    public static void enfermeiro(TipoUsuario tipoUsuario) {
        verificaPermissao(tipoUsuario, Set.of(TipoUsuario.ENFERMEIRO));
    }

    public static void adminEEnfermeiro(TipoUsuario tipoUsuario) {
        verificaPermissao(tipoUsuario, Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO));
    }

    public static void medicoEEnfermeiro(TipoUsuario tipoUsuario) {
        verificaPermissao(tipoUsuario, Set.of(TipoUsuario.MEDICO, TipoUsuario.ENFERMEIRO));
    }

    public static void adminOuApenasProprio(UsuarioAutenticado usuarioAutenticado, UUID alvoUuid) {
        apenasOProprio(usuarioAutenticado, alvoUuid);
    }

}
