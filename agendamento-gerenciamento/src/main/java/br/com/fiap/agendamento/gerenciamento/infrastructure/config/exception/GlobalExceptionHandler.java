package br.com.fiap.agendamento.gerenciamento.infrastructure.config.exception;

import br.com.fiap.agendamento.gerenciamento.domain.exception.RegraDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
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

        var erroResposta = new ErroResposta(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Erro de validação nos campos da requisição",
                camposErros
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResposta> handleRegraDeNegocioException(RegraDeNegocioException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErroResposta(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        ex.getMessage(),
                        new ArrayList<>()
                )
        );
    }

}
