package br.edu.utfpr.sofrimento.services;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.utfpr.sofrimento.dtos.SensorDTO;
import br.edu.utfpr.sofrimento.exception.NotFoundException;
import br.edu.utfpr.sofrimento.models.Device;
import br.edu.utfpr.sofrimento.models.Sensor;
import br.edu.utfpr.sofrimento.repositories.DeviceRepository;
import br.edu.utfpr.sofrimento.repositories.SensorRepository;

@Service
public class SensorService {
    private final SensorRepository sensorRepo;
    private final DeviceRepository deviceRepo;
    Logger logger = Logger.getLogger(SensorRepository.class.getName());

    public SensorService(SensorRepository sensorRepo, DeviceRepository deviceRepo) {
        this.sensorRepo = sensorRepo;
        this.deviceRepo = deviceRepo;
    }

    //CREATE
    public SensorDTO save(String deviceId, SensorDTO sensorDTO) {
        Device device = deviceRepo.findById(UUID.fromString(deviceId))
                .orElseThrow(() -> new NotFoundException("Sensor " + deviceId + " não encontrada."));

        Sensor sensor = new Sensor(); // Cria um sensor vazio
        BeanUtils.copyProperties(sensorDTO, sensor, "id"); // Copia as propriedades do DTO para o sensor
        sensor.setDevice(device); // Associando a device ao novo sensor

        logger.info("Criando sensor: " + device);
        
        Sensor saved = sensorRepo.save(sensor);
        return new SensorDTO(saved.getId(), saved.getPhysicalID(), saved.getSensorType());
    }

    //READ BY PERSON
    public Page<SensorDTO> listByDevice(String deviceId, int page, int size) {
        return sensorRepo.findByDeviceId(UUID.fromString(deviceId), PageRequest.of(page, size))
                .map(l -> new SensorDTO(l.getId(), l.getPhysicalID(), l.getSensorType()));
    }

    /**
     * //READ BY ID
     * 
     * @param id
     * @return
     */
    public Sensor findById(String id) {
        return sensorRepo.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Sensor não encontrada com ID: " + id));
    }

    /**
     * //READ ALL
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<SensorDTO> listAll(int page, int size) {
        return sensorRepo.findAll(PageRequest.of(page, size)).map(l -> new SensorDTO(l.getId(), l.getPhysicalID(), l.getSensorType()));
    }

    /**
     * DELETE BY ID
     * 
     * @param id
     */
    public void delete(String id) {

        logger.info("Deletando sensor com ID: " + id);

        sensorRepo.delete(findById(id));
    }

    /**
     * UPDATE BY ID
     * Converte um objeto DeviceDTO para a entidade Device e a atualiza.
     * 
     * @param id
     * @param sensorDTO
     * @return
     */
    public Sensor update(String id, SensorDTO sensorDTO) {
        var existingSensor = findById(id);
        BeanUtils.copyProperties(sensorDTO, existingSensor, "id");

        logger.info("Atualizando sensor: " + existingSensor);

        return sensorRepo.save(existingSensor);
    }
}

