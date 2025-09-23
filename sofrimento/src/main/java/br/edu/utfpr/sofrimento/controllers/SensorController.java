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

import br.edu.utfpr.sofrimento.dtos.SensorDTO;
import br.edu.utfpr.sofrimento.models.Sensor;
import br.edu.utfpr.sofrimento.services.SensorService;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    //CREATE
    @PostMapping(value = {"/{deviceId}", "/{deviceId}/"})
    public SensorDTO create(@PathVariable String deviceId, @RequestBody SensorDTO dto) {
        return service.save(deviceId, dto);
    }

    //GET ALL
    @GetMapping(value = {"", "/"})
    public Page<SensorDTO> listAll(
            @RequestParam String deviceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
            if (deviceId == null)     
                return service.listAll(page, size);
            else 
                return service.listByDevice(deviceId, page, size);
    }

    //GET BY ID
    @GetMapping(value = {"/{sensorId}","/{sensorId}/"})
    public Sensor findById(
            @PathVariable String sensorId) {
        return service.findById(sensorId);
    }

    //GET BY PERSON ID
    @GetMapping(value = {"/device","/device"})
    public Page<SensorDTO> listByDevice(
            @RequestParam String deviceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.listByDevice(deviceId, page, size);
    }

    // DELETE
    @DeleteMapping(value = {"/{sensorId}","/{sensorId}/"})
    public void delete(@PathVariable String sensorId) {
        service.delete(sensorId);
    }

    /**
     * Endpoint para atualizar uma device existente.
     * Recebe o ID da device como parâmetro de caminho e um objeto DeviceDTO no
     * corpo da requisição.
     * Retorna a device atualizada.
     * 
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = {"/{sensorId}","/{sensorId}/"})
    public Sensor update(@PathVariable String sensorId, @RequestBody SensorDTO dto) {
        return service.update(sensorId, dto);
    }
}
