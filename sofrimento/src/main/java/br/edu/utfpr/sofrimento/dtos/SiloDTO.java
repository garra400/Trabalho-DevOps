package br.edu.utfpr.sofrimento.dtos;

import java.util.UUID;

public record SiloDTO(UUID id, 
        @NotBlank
        @Size(min = 2, max = 255)
        Integer physicalId) { // Tem que ter os mesmos nomes dos atributos

}