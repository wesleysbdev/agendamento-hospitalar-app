package br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper;

import org.mapstruct.Mapper;

import java.time.Duration;

@Mapper(componentModel = "spring")
public interface DurationMapper {

    default Duration paraDuration(Integer minutos) {
        return minutos == null
                ? null
                : Duration.ofMinutes(minutos);
    }

    default Integer paraMinutos(Duration duration) {
        return duration == null
                ? null
                : Math.toIntExact(duration.toMinutes());
    }
}