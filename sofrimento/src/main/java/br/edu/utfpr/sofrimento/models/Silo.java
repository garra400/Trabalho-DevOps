package br.edu.utfpr.sofrimento.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // Em cima da classe, cria os get e set automaticamente
@Setter
@ToString
@Entity     //Indica que é uma entidade a ser preservada no database
@Table(name="tb_silo") //Nome da tabela
public class Silo extends BaseEntity{
    @Column(name = "physical_id", nullable=false)
    private Integer physicalId;
}
