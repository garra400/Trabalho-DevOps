package br.edu.utfpr.sofrimento.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.sofrimento.models.Person;

public interface PersonRepository extends JpaRepository<Person, UUID>{
}
