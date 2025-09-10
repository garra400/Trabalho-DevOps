package br.edu.utfpr.sofrimento.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
