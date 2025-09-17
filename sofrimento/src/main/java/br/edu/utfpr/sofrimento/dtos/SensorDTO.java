package br.edu.utfpr.sofrimento.dtos;

import java.util.UUID;

import br.edu.utfpr.sofrimento.models.SensorType;

public record SensorDTO(UUID id, String physicalID, SensorType sensorType) { // Tem que ter os mesmos nomes dos atributos

}

