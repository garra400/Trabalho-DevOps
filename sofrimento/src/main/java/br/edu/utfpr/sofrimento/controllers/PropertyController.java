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

import br.edu.utfpr.sofrimento.dtos.PropertyDTO;
import br.edu.utfpr.sofrimento.models.Property;
import br.edu.utfpr.sofrimento.services.PropertyService;

@RestController
@RequestMapping("/property")
public class PropertyController {

    private final PropertyService service;

    public PropertyController(PropertyService service) {
        this.service = service;
    }

    //CREATE
    @PostMapping(value = {"/{personId}", "/{personId}/"})
    public PropertyDTO create(@PathVariable String personId, @RequestBody PropertyDTO dto) {
        return service.save(personId, dto);
    }

    //GET ALL
    @GetMapping(value = {"", "/"})
    public Page<PropertyDTO> listAll(
            @RequestParam String personId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
            if (personId == null)     
                return service.listAll(page, size);
            else 
                return service.listByPerson(personId, page, size);
    }

    //GET BY ID
    @GetMapping(value = {"/{propertyId}","/{propertyId}/"})
    public Property findById(
            @PathVariable String propertyId) {
        return service.findById(propertyId);
    }

    //GET BY PERSON ID
    @GetMapping(value = {"/person","/person"})
    public Page<PropertyDTO> listByPerson(
            @RequestParam String personId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.listByPerson(personId, page, size);
    }

    // DELETE
    @DeleteMapping(value = {"/{propertyId}","/{propertyId}/"})
    public void delete(@PathVariable String propertyId) {
        service.delete(propertyId);
    }

    /**
     * Endpoint para atualizar uma person existente.
     * Recebe o ID da person como parâmetro de caminho e um objeto PersonDTO no
     * corpo da requisição.
     * Retorna a person atualizada.
     * 
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = {"/{propertyId}","/{propertyId}/"})
    public Property update(@PathVariable String propertyId, @RequestBody PropertyDTO dto) {
        return service.update(propertyId, dto);
    }
}