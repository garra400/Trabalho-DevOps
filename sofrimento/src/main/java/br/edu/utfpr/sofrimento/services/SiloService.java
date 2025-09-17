package br.edu.utfpr.sofrimento.services;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.edu.utfpr.sofrimento.dtos.SiloDTO;
import br.edu.utfpr.sofrimento.exception.NotFoundException;
import br.edu.utfpr.sofrimento.models.Property;
import br.edu.utfpr.sofrimento.models.Silo;
import br.edu.utfpr.sofrimento.repositories.PropertyRepository;
import br.edu.utfpr.sofrimento.repositories.SiloRepository;

public class SiloService {
    private final SiloRepository siloRepo;
    private final PropertyRepository propertyRepo;
    Logger logger = Logger.getLogger(SiloRepository.class.getName());

    public SiloService(SiloRepository siloRepo, PropertyRepository propertyRepo) {
        this.siloRepo = siloRepo;
        this.propertyRepo = propertyRepo;
    }

    //CREATE
    public SiloDTO save(String propertyId, SiloDTO siloDTO) {
        Property property = propertyRepo.findById(UUID.fromString(propertyId))
                .orElseThrow(() -> new NotFoundException("Silo " + propertyId + " não encontrada."));

        Silo silo = new Silo(); // Cria um silo vazio
        BeanUtils.copyProperties(siloDTO, silo, "id"); // Copia as propriedades do DTO para o silo
        silo.setProperty(property); // Associando a property ao novo silo

        logger.info("Criando silo: " + property);
        
        Silo saved = siloRepo.save(silo);
        return new SiloDTO(saved.getId(), saved.getPhysicalId());
    }

    //READ BY PERSON
    public Page<SiloDTO> listByProperty(String propertyId, int page, int size) {
        return siloRepo.findByPropertyId(UUID.fromString(propertyId), PageRequest.of(page, size))
                .map(l -> new SiloDTO(l.getId(), l.getPhysicalId()));
    }

    /**
     * //READ BY ID
     * 
     * @param id
     * @return
     */
    public Silo findById(String id) {
        return siloRepo.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Silo não encontrada com ID: " + id));
    }

    /**
     * //READ ALL
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<SiloDTO> listAll(int page, int size) {
        return siloRepo.findAll(PageRequest.of(page, size)).map(l -> new SiloDTO(l.getId(), l.getPhysicalId()));
    }

    /**
     * DELETE BY ID
     * 
     * @param id
     */
    public void delete(String id) {

        logger.info("Deletando silo com ID: " + id);

        siloRepo.delete(findById(id));
    }

    /**
     * UPDATE BY ID
     * Converte um objeto PropertyDTO para a entidade Property e a atualiza.
     * 
     * @param id
     * @param siloDTO
     * @return
     */
    public Silo update(String id, SiloDTO siloDTO) {
        var existingSilo = findById(id);
        BeanUtils.copyProperties(siloDTO, existingSilo, "id");

        logger.info("Atualizando silo: " + existingSilo);

        return siloRepo.save(existingSilo);
    }
}
