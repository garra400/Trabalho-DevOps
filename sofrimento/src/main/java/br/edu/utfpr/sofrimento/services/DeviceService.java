package br.edu.utfpr.sofrimento.services;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.edu.utfpr.sofrimento.dtos.DeviceDTO;
import br.edu.utfpr.sofrimento.exception.NotFoundException;
import br.edu.utfpr.sofrimento.models.Device;
import br.edu.utfpr.sofrimento.models.Silo;
import br.edu.utfpr.sofrimento.repositories.DeviceRepository;
import br.edu.utfpr.sofrimento.repositories.SiloRepository;


public class DeviceService {
    private final DeviceRepository deviceRepo;
    private final SiloRepository siloRepo;

    public DeviceService(DeviceRepository deviceRepo, SiloRepository siloRepo) {
        this.deviceRepo = deviceRepo;
        this.siloRepo = siloRepo;
    }

    //CREATE
    public DeviceDTO save(String siloId, DeviceDTO deviceDTO) {
        Silo silo = siloRepo.findById(UUID.fromString(siloId))
                .orElseThrow(() -> new NotFoundException("Silo " + siloId + " não encontrado."));

        Device device = new Device(); // Cria um device vazio
        BeanUtils.copyProperties(deviceDTO, device, "id"); // Copia as propriedades do DTO para o device
        device.setSilo(silo); // Associando o silo ao novo device

        Device saved = deviceRepo.save(device);
        return new DeviceDTO(saved.getId(), saved.getMac(), saved.getIp());
    }

    //READ BY SILO
    public Page<DeviceDTO> listBySilo(String siloId, int page, int size) {
        return deviceRepo.findBySiloId(UUID.fromString(siloId), PageRequest.of(page, size))
                .map(l -> new DeviceDTO(l.getId(), l.getMac(), l.getIp()));
    }

    /**
     * //READ BY ID
     * 
     * @param id
     * @return
     */
    public Device findById(String id) {
        return deviceRepo.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Device não encontrada com ID: " + id));
    }

    /**
     * //READ ALL
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<DeviceDTO> listAll(int page, int size) {
        return deviceRepo.findAll(PageRequest.of(page, size)).map(l -> new DeviceDTO(l.getId(), l.getMac(), l.getIp()));
    }

    /**
     * DELETE BY ID
     * 
     * @param id
     */
    public void delete(String id) {
        //logger.info("Deletando device com ID: " + id);
        deviceRepo.delete(findById(id));
    }

    /**
     * UPDATE BY ID
     * Converte um objeto DeviceDTO para a entidade Device e a atualiza.
     * 
     * @param id
     * @param deviceDTO
     * @return
     */
    public Device update(String id, DeviceDTO deviceDTO) {
        var existingDevice = findById(id);
        BeanUtils.copyProperties(deviceDTO, existingDevice, "id");

        //logger.info("Atualizando device: " + existingDevice);

        return deviceRepo.save(existingDevice);
    }
}
