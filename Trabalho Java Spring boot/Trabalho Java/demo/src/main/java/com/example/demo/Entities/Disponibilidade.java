package com.example.demo.Entities;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "Disponibilidade")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class Disponibilidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(nullable = false)
    private String diaSemana;

    @Column(nullable = false)
    private LocalTime horarioinicio;

    @Column(nullable = false)
    private LocalTime horarioFim;

    public String getDiaDaSemana() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDiaDaSemana'");
    }

    public LocalTime getHorarioInicio() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHorarioInicio'");
    }

}
