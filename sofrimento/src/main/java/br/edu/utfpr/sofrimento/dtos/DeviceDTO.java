package br.edu.utfpr.sofrimento.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeviceDTO(UUID id, @NotBlank String mac, @NotBlank @Size String ip) { // Tem que ter os mesmos nomes dos atributos

}
