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
@Table(name = "tb_property")
public class Property extends BaseEntity{

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Person person;
}
