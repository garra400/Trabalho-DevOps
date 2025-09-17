package br.edu.utfpr.sofrimento.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.sofrimento.models.Property;

public interface PropertyRepository extends JpaRepository<Property, UUID>{
    Page<Property> findByPersonId(UUID personId, Pageable pageable);
}
