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
@Table(name = "tb_sensor")
public class Sensor extends BaseEntity {
    
    @Column(name = "physical_id", nullable = false)
    private String physicalID;
    @Column(name = "sensor_type", nullable = false)
    private SensorType sensorType;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Device device;
}
