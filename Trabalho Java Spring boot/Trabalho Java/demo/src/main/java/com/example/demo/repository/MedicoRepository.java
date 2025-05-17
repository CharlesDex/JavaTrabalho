package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Especialidade;
import com.example.demo.Entities.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByEspecialidade(Especialidade especialidade);
    Optional<Medico> findByCrm(String crm);
}