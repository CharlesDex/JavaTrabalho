package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Consulta;
import com.example.demo.Entities.Medico;
import com.example.demo.Entities.Pacientes;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByMedicoAndDataHoraBetween(Medico medico, LocalDateTime inicio, LocalDateTime fim);
    List<Consulta> findByPaciente(Pacientes paciente);
    List<Consulta> findByStatus(String status);
    List<Consulta> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    boolean existsByMedicoAndDataHora(Medico medico, LocalDateTime dataHora);
}