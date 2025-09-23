package br.edu.utfpr.sofrimento.services;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.utfpr.sofrimento.dtos.PropertyDTO;
import br.edu.utfpr.sofrimento.exception.NotFoundException;
import br.edu.utfpr.sofrimento.models.Person;
import br.edu.utfpr.sofrimento.models.Property;
import br.edu.utfpr.sofrimento.repositories.PersonRepository;
import br.edu.utfpr.sofrimento.repositories.PropertyRepository;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepo;
    private final PersonRepository personRepo;
    Logger logger = Logger.getLogger(PropertyRepository.class.getName());

    public PropertyService(PropertyRepository propertyRepo, PersonRepository personRepo) {
        this.propertyRepo = propertyRepo;
        this.personRepo = personRepo;
    }

    //CREATE
    public PropertyDTO save(String personId, PropertyDTO propertyDTO) {
        Person person = personRepo.findById(UUID.fromString(personId))
                .orElseThrow(() -> new NotFoundException("Person " + personId + " não encontrada."));

        Property property = new Property(); // Cria um property vazio
        BeanUtils.copyProperties(propertyDTO, property, "id"); // Copia as propriedades do DTO para o property
        property.setPerson(person); // Associando a person ao novo property
        
        logger.info("Criando property: " + property);

        Property saved = propertyRepo.save(property);
        return new PropertyDTO(saved.getId(), saved.getName());
    }

    //READ BY PERSON
    public Page<PropertyDTO> listByPerson(String personId, int page, int size) {
        return propertyRepo.findByPersonId(UUID.fromString(personId), PageRequest.of(page, size))
                .map(l -> new PropertyDTO(l.getId(), l.getName()));
    }

    /**
     * //READ BY ID
     * 
     * @param id
     * @return
     */
    public Property findById(String id) {
        return propertyRepo.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Property não encontrada com ID: " + id));
    }

    /**
     * //READ ALL
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<PropertyDTO> listAll(int page, int size) {
        return propertyRepo.findAll(PageRequest.of(page, size)).map(l -> new PropertyDTO(l.getId(), l.getName()));
    }

    /**
     * DELETE BY ID
     * 
     * @param id
     */
    public void delete(String id) {
    
        logger.info("Deletando property com ID: " + id);

        propertyRepo.delete(findById(id));
    }

    /**
     * UPDATE BY ID
     * Converte um objeto PersonDTO para a entidade Person e a atualiza.
     * 
     * @param id
     * @param propertyDTO
     * @return
     */
    public Property update(String id, PropertyDTO propertyDTO) {
        var existingProperty = findById(id);
        BeanUtils.copyProperties(propertyDTO, existingProperty, "id");

        logger.info("Atualizando property: " + existingProperty);

        return propertyRepo.save(existingProperty);
    }
}
