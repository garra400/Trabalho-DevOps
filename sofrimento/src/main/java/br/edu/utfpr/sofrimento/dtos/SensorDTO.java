package br.edu.utfpr.sofrimento.dtos;

import java.util.UUID;

import br.edu.utfpr.sofrimento.models.SensorType;
import jakarta.validation.constraints.NotBlank;

public record SensorDTO(UUID id, @NotBlank String physicalID, @NotBlank SensorType sensorType) { // Tem que ter os mesmos nomes dos atributos

}

