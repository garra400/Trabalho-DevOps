package br.edu.utfpr.sofrimento.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_sensor")
public class Sensor extends BaseEntity {
    @Column(name = "physical_id", nullable = false)
    private String physicalID;
    @Column(name = "sensor_type", nullable = false)
    private SensorType sensorType;
    @ManyToOne
    @Column(nullable = false)
    private Silo silo;
}
