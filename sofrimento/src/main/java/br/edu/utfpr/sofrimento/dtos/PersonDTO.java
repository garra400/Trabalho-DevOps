package br.edu.utfpr.sofrimento.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record PersonDTO(UUID id, @NotBlank String name) { // Tem que ter os mesmos nomes dos atributos

}