package br.edu.utfpr.sofrimento.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_device")

public class Device extends BaseEntity {

    @Column(name = "mac", updatable = false, length = 200, nullable = false)
    private String mac;

    @Column(name = "ip", length = 200, nullable = false)
    private String ip;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Silo silo;
}