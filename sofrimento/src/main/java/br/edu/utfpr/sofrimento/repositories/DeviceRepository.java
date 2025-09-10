package br.edu.utfpr.sofrimento.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.sofrimento.models.Device;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

}

