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

import br.edu.utfpr.sofrimento.dtos.PersonDTO;
import br.edu.utfpr.sofrimento.models.Person;
import br.edu.utfpr.sofrimento.services.PersonService;

/**
 * Controlador REST para gerenciar operações relacionadas a persons.
 * Ele fornece endpoints para criar, atualizar, deletar e listar persons.
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    // Injeção do serviço de person
    private final PersonService service;

    /**
     * Construtor da classe PersonController.
     * Injeta a dependência do serviço de person.
     * 
     * @param service
     */
    public PersonController(PersonService service) {
        this.service = service;
    }

    /**
     * Endpoint para criar uma nova person.
     * Recebe um objeto PersonDTO no corpo da requisição e retorna a person criada.
     */
    @PostMapping
    public Person create(@RequestBody PersonDTO dto) {
        return service.save(dto);
    }

    /**
     * Endpoint para listar persons com paginação.
     * Recebe parâmetros de consulta para página e tamanho, retornando uma página de
     * persons.
     */
    @GetMapping
    public Page<Person> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.listAll(page, size);
    }

    /**
     * Endpoint para obter uma person por ID.
     * Recebe o ID da person como parâmetro de caminho e retorna a person
     * correspondente.
     */
    @GetMapping("/{id}")
    public Person getById(@PathVariable String id) {
        return service.findById(id);
    }

    /**
     * Endpoint para deletar uma person por ID.
     * Recebe o ID da person como parâmetro de consulta e deleta a person
     * correspondente.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
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
    @PutMapping("/{id}")
    public Person update(@PathVariable String id, @RequestBody PersonDTO dto) {
        return service.update(id, dto);
    }
}