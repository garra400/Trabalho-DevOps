package br.edu.utfpr.sofrimento.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "Mac", updatable = false, length = 200, nullable = false)
    private String mac;

    @Column(name = "Ip", length = 200, nullable = false)
    private String ip;

}