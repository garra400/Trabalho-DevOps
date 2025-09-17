package br.edu.utfpr.sofrimento.dtos;

import java.util.UUID;

public record DeviceDTO(UUID id, String mac, String ip) { // Tem que ter os mesmos nomes dos atributos

}
