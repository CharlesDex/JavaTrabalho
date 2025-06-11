package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entities.Pacientes;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Pacientes, Long> {
    Optional<Pacientes> findByid(Long id);

    Optional<Pacientes> findByNome(String Nome);

    Optional<Pacientes> findByCpf(String Cpf);

    Optional<Pacientes> findByEmail(String Email);

    Optional<Pacientes> findByTelefone(String Telefone);

}