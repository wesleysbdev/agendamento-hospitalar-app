package br.com.fiap.agendamento.notificacao.domain.notificacao.entity;

import br.com.fiap.agendamento.notificacao.domain.notificacao.enums.TipoNotificacao;
import br.com.fiap.agendamento.notificacao.domain.notificacao.exception.NotificacaoDadosInvalidosException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notificacao {

    private final UUID uuid;
    private String destinatario;
    private String assunto;
    private String mensagem;
    private LocalDateTime dataEnvio;
    private boolean enviada;

    public Notificacao(UUID uuid, String destinatario, String assunto, String mensagem, LocalDateTime dataEnvio, boolean enviada) {
        validarDadosObrigatorios(uuid, destinatario, assunto, mensagem);
        this.uuid = uuid;
        this.destinatario = destinatario;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
        this.enviada = enviada;
    }

    public void marcarComoEnviada() {
        this.enviada = true;
        this.dataEnvio = LocalDateTime.now();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getAssunto() {
        return assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public boolean isEnviada() {
        return enviada;
    }

    private static void validarDadosObrigatorios(UUID uuid, String destinatario, String assunto, String mensagem) {
        if (uuid == null || uuid.toString().isBlank()) {
            throw new NotificacaoDadosInvalidosException("UUID é obrigatório.");
        }

        if (destinatario == null || destinatario.isBlank()) {
            throw new NotificacaoDadosInvalidosException("Destinatário é obrigatório.");
        }

        if (assunto == null || assunto.isBlank()) {
            throw new NotificacaoDadosInvalidosException("Assunto é obrigatório.");
        }

        if (mensagem == null || mensagem.isBlank()) {
            throw new NotificacaoDadosInvalidosException("Mensagem é obrigatória.");
        }
    }
}
