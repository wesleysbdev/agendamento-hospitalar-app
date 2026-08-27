package br.com.fiap.agendamento.gerenciamento.infrastructure.config.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErroResposta.CampoErro> camposErros = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    String nomeCampo = ((FieldError) error).getField();
                    String mensagemErro = error.getDefaultMessage();
                    return new ErroResposta.CampoErro(nomeCampo, mensagemErro);
                })
                .toList();

        return montarResposta(HttpStatus.BAD_REQUEST, "Erro de validação nos campos da requisição", "Requisição inválida", camposErros);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResposta> handleRegraDeNegocioException(RegraDeNegocioException ex) {
        return montarResposta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<ErroResposta> handleHibernateException(HibernateException ex) {
        return montarResposta(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "Erro interno do servidor", new ArrayList<>());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResposta> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {

        String message = ex.getMessage();

        // Se o erro for especificamente em um campo de data
        if (ex.getRequiredType() != null && ex.getRequiredType().equals(LocalDate.class)) {
            message = String.format("O parâmetro '%s' deve estar no formato ISO (YYYY-MM-DD). Valor recebido: '%s'",
                    ex.getName(), ex.getValue());
        } else {
            message = String.format("O parâmetro '%s' recebeu um valor inválido.", ex.getName());
        }

        return montarResposta(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroResposta> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {

        String message = String.format("O parâmetro obrigatório '%s' não foi enviado.", ex.getParameterName());

        return montarResposta(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErroResposta> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return montarResposta(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(JwtEncodingException.class)
    public ResponseEntity<ErroResposta> handleJwtEncodingException(JwtEncodingException ex) {
        return montarResposta(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<ErroResposta> montarResposta(HttpStatusCode httpStatus, String message) {
        return montarResposta(httpStatus, message, "Requisição Inválida", new ArrayList<>());
    }

    private ResponseEntity<ErroResposta> montarResposta(
            HttpStatusCode httpStatus,
            String message,
            String erro,
            List<ErroResposta.CampoErro> campos
    ) {
        return ResponseEntity.status(httpStatus).body(new ErroResposta(
                Instant.now(),
                httpStatus.value() != 0 ? httpStatus.value() : HttpStatus.BAD_REQUEST.value(),
                !erro.isBlank() ? erro : "Requisição Inválida",
                message,
                !campos.isEmpty() ? campos : new ArrayList<>()
        ));
    }

}
