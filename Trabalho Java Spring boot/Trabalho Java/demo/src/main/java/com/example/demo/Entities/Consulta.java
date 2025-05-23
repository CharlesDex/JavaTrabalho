package com.example.demo.Entities;

import java.time.LocalDateTime;

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
@Table(name = "Consulta")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
// atualização
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Pacientes paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(nullable = false, unique = true)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String obseravacoes;

    public void setObservacoes(String observacoes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setObservacoes'");
    }

}
