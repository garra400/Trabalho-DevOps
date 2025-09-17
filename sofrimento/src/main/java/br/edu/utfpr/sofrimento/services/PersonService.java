package br.edu.utfpr.sofrimento.services;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.utfpr.sofrimento.dtos.PersonDTO;
import br.edu.utfpr.sofrimento.exception.NotFoundException;
import br.edu.utfpr.sofrimento.models.Person;
import br.edu.utfpr.sofrimento.repositories.PersonRepository;


/**
 * Serviço para gerenciar operações relacionadas a persons.
 * Ele fornece métodos CRUD para persons.
 * 
 * Utiliza o repositório PersonRepository para interagir com o banco de dados.
 */
@Service
public class PersonService {
    Logger logger = Logger.getLogger(PersonService.class.getName());

    // Injeção do repositório de person
    private final PersonRepository repository;

    /**
     * Construtor da classe PersonService.
     * Injeta a dependência do repositório de person.
     * 
     * @param repository
     */
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva uma nova person no banco de dados.
     * Converte um objeto PersonDTO para a entidade Person e a salva.
     * 
     * @param personDTO
     * @return
     */
    public Person save(PersonDTO personDTO) {
        var person = new Person();
        BeanUtils.copyProperties(personDTO, person, "id");

        logger.info("Criando person: " + person);

        return repository.save(person);
    }

    /**
     * Lista todas as persons com pageção.
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<Person> listAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    /**
     * Busca uma person pelo ID.
     * 
     * @param id
     * @return
     */
    public Person findById(String id) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Person não encontrada com ID: " + id));
    }

    /**
     * Deleta uma person pelo ID.
     * 
     * @param id
     */
    public void delete(String id) {
        logger.info("Deletando person com ID: " + id);

        repository.delete(findById(id));
    }

    /**
     * Atualiza uma person existente.
     * Converte um objeto PersonDTO para a entidade Person e a atualiza.
     * 
     * @param id
     * @param personDTO
     * @return
     */
    public Person update(String id, PersonDTO personDTO) {
        var existingPerson = findById(id);
        BeanUtils.copyProperties(personDTO, existingPerson, "id");

        logger.info("Atualizando person: " + existingPerson);

        return repository.save(existingPerson);
    }
}
