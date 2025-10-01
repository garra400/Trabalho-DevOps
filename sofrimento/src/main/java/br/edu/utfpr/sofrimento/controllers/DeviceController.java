package br.edu.utfpr.sofrimento.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.sofrimento.dtos.DeviceDTO;
import br.edu.utfpr.sofrimento.models.Device;
import br.edu.utfpr.sofrimento.services.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/device")
@Tag(name = "Device", description = "API para gerenciamento de dispositivos IoT")
@SecurityRequirement(name = "bearerAuth")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @Operation(
        summary = "Criar novo dispositivo",
        description = "Cria um novo dispositivo associado a um silo específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Dispositivo criado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DeviceDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Silo não encontrado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado - Token inválido ou ausente",
            content = @Content
        )
    })
    @PostMapping(value = {"/{siloId}", "/{siloId}/"})
    public ResponseEntity<DeviceDTO> create(
            @Parameter(description = "ID do silo ao qual o dispositivo será associado", required = true)
            @PathVariable String siloId,
            @Parameter(description = "Dados do dispositivo a ser criado", required = true)
            @Valid @RequestBody DeviceDTO dto) {
        DeviceDTO created = service.save(siloId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
        summary = "Listar todos os dispositivos",
        description = "Retorna uma lista paginada de todos os dispositivos. Pode filtrar por silo."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de dispositivos retornada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Page.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado - Token inválido ou ausente",
            content = @Content
        )
    })
    @GetMapping(value = {"", "/"})
    public ResponseEntity<Page<DeviceDTO>> listAll(
            @Parameter(description = "ID do silo para filtrar dispositivos (opcional)")
            @RequestParam(required = false) String siloId,
            @Parameter(description = "Número da página (começa em 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página")
            @RequestParam(defaultValue = "10") int size) {
        Page<DeviceDTO> devices;
        if (siloId == null || siloId.isEmpty()) {
            devices = service.listAll(page, size);
        } else {
            devices = service.listBySilo(siloId, page, size);
        }
        return ResponseEntity.ok(devices);
    }

    @Operation(
        summary = "Buscar dispositivo por ID",
        description = "Retorna os detalhes de um dispositivo específico pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Dispositivo encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Device.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Dispositivo não encontrado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado - Token inválido ou ausente",
            content = @Content
        )
    })
    @GetMapping(value = {"/{deviceId}", "/{deviceId}/"})
    public ResponseEntity<Device> findById(
            @Parameter(description = "ID do dispositivo a ser buscado", required = true)
            @PathVariable String deviceId) {
        Device device = service.findById(deviceId);
        return ResponseEntity.ok(device);
    }

    @Operation(
        summary = "Listar dispositivos por silo",
        description = "Retorna uma lista paginada de dispositivos associados a um silo específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de dispositivos do silo retornada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Page.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado - Token inválido ou ausente",
            content = @Content
        )
    })
    @GetMapping(value = {"/silo", "/silo/"})
    public ResponseEntity<Page<DeviceDTO>> listBySilo(
            @Parameter(description = "ID do silo", required = true)
            @RequestParam String siloId,
            @Parameter(description = "Número da página (começa em 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página")
            @RequestParam(defaultValue = "10") int size) {
        Page<DeviceDTO> devices = service.listBySilo(siloId, page, size);
        return ResponseEntity.ok(devices);
    }

    @Operation(
        summary = "Deletar dispositivo",
        description = "Remove um dispositivo do sistema pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Dispositivo deletado com sucesso",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Dispositivo não encontrado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado - Token inválido ou ausente",
            content = @Content
        )
    })
    @DeleteMapping(value = {"/{deviceId}", "/{deviceId}/"})
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do dispositivo a ser deletado", required = true)
            @PathVariable String deviceId) {
        service.delete(deviceId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Atualizar dispositivo",
        description = "Atualiza os dados de um dispositivo existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Dispositivo atualizado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Device.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Dispositivo não encontrado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado - Token inválido ou ausente",
            content = @Content
        )
    })
    @PutMapping(value = {"/{deviceId}", "/{deviceId}/"})
    public ResponseEntity<Device> update(
            @Parameter(description = "ID do dispositivo a ser atualizado", required = true)
            @PathVariable String deviceId,
            @Parameter(description = "Novos dados do dispositivo", required = true)
            @Valid @RequestBody DeviceDTO dto) {
        Device updated = service.update(deviceId, dto);
        return ResponseEntity.ok(updated);
    }
}