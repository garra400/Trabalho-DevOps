package br.edu.utfpr.sofrimento.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SiloDTO(UUID id, 
        @NotBlank
        @Size(min = 1, max = 255)
        Integer physicalId) { // Tem que ter os mesmos nomes dos atributos

}