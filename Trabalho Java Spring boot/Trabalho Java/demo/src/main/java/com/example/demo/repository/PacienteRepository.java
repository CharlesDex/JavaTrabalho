package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Pacientes;

public interface PacienteRepository extends JpaRepository<Pacientes, Long> {
    Optional<Pacientes> findByCpf(String cpf);
}