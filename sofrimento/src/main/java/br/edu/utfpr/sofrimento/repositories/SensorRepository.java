package br.edu.utfpr.sofrimento.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.sofrimento.models.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, UUID>{
    Page<Sensor> findByDeviceId(UUID deviceId, Pageable pageable);
}