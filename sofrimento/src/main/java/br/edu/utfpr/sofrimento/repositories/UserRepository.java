package br.edu.utfpr.sofrimento.repositories;

import br.edu.utfpr.sofrimento.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Métodos customizados se necessário
}
