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

import br.edu.utfpr.sofrimento.dtos.SiloDTO;
import br.edu.utfpr.sofrimento.models.Silo;
import br.edu.utfpr.sofrimento.services.SiloService;

@RestController
@RequestMapping("/silo")
public class SiloController {

    private final SiloService service;

    public SiloController(SiloService service) {
        this.service = service;
    }

    //CREATE
    @PostMapping(value = {"/{propertyId}", "/{propertyId}/"})
    public SiloDTO create(@PathVariable String propertyId, @RequestBody SiloDTO dto) {
        return service.save(propertyId, dto);
    }

    //GET ALL
    @GetMapping(value = {"", "/"})
    public Page<SiloDTO> listAll(
            @RequestParam String propertyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
            if (propertyId == null)     
                return service.listAll(page, size);
            else 
                return service.listByProperty(propertyId, page, size);
    }

    //GET BY ID
    @GetMapping(value = {"/{siloId}","/{siloId}/"})
    public Silo findById(
            @PathVariable String siloId) {
        return service.findById(siloId);
    }

    //GET BY PERSON ID
    @GetMapping(value = {"/property","/property"})
    public Page<SiloDTO> listByProperty(
            @RequestParam String propertyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.listByProperty(propertyId, page, size);
    }

    // DELETE
    @DeleteMapping(value = {"/{siloId}","/{siloId}/"})
    public void delete(@PathVariable String siloId) {
        service.delete(siloId);
    }

    /**
     * Endpoint para atualizar uma property existente.
     * Recebe o ID da property como parâmetro de caminho e um objeto PropertyDTO no
     * corpo da requisição.
     * Retorna a property atualizada.
     * 
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = {"/{siloId}","/{siloId}/"})
    public Silo update(@PathVariable String siloId, @RequestBody SiloDTO dto) {
        return service.update(siloId, dto);
    }
}