package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    // Busca por nome exato
    Optional<Especialidade> findByNome(String nome);
    
    // Busca contendo parte do nome (case insensitive)
    List<Especialidade> findByNomeContainingIgnoreCase(String nome);
    
    // Verifica se existem médicos vinculados
    boolean existsByMedicosNotEmpty();
}