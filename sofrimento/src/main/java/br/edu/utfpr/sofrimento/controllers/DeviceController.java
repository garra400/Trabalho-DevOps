package br.edu.utfpr.sofrimento.controllers;

import org.springframework.data.domain.Page;
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

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    //CREATE
    @PostMapping(value = {"/{siloId}", "/{siloId}/"})
    public DeviceDTO create(@PathVariable String siloId, @RequestBody DeviceDTO dto) {
        return service.save(siloId, dto);
    }

    //GET ALL
    @GetMapping(value = {"", "/"})
    public Page<DeviceDTO> listAll(
            @RequestParam String siloId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
            if (siloId == null)
                return service.listAll(page, size);
            else
                return service.listBySilo(siloId, page, size);
    }

    //GET BY ID
    @GetMapping(value = {"/{deviceId}","/{deviceId}/"})
    public Device findById(
            @PathVariable String deviceId) {
        return service.findById(deviceId);
    }

    //GET BY SILO ID
    @GetMapping(value = {"/silo","/silo"})
    public Page<DeviceDTO> listBySilo(
            @RequestParam String siloId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.listBySilo(siloId, page, size);
    }

    // DELETE
    @DeleteMapping(value = {"/{deviceId}","/{deviceId}/"})
    public void delete(@PathVariable String deviceId) {
        service.delete(deviceId);
    }

    /**
     * Endpoint para atualizar um device existente.
     * Recebe o ID do device como parâmetro de caminho e um objeto DeviceDTO no
     * corpo da requisição.
     * Retorna o device atualizado.
     * 
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = {"/{deviceId}","/{deviceId}/"})
    public Device update(@PathVariable String deviceId, @RequestBody DeviceDTO dto) {
        return service.update(deviceId, dto);
    }
}