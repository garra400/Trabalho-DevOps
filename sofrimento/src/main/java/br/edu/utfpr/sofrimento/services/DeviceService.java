package br.edu.utfpr.sofrimento.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.sofrimento.models.Device;
import br.edu.utfpr.sofrimento.models.Silo;
import br.edu.utfpr.sofrimento.repositories.DeviceRepository;
import br.edu.utfpr.sofrimento.repositories.SiloRepository;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SiloRepository siloRepository;

    public Device createDevice(String mac, String ip, UUID siloId) {
        Silo silo = siloRepository.findById(siloId)
                .orElseThrow(() -> new IllegalArgumentException("Silo not found"));

        Device device = new Device();
        device.setMac(mac);
        device.setIp(ip);
        device.setSilo(silo);

        return deviceRepository.save(device);
    }
}
