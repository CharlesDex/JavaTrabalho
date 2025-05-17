package com.example.demo.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entities.Disponibilidade;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {
    // Lista todas as disponibilidades de um médico
    List<Disponibilidade> findByMedicoId(Long medicoId);
    
    // Busca disponibilidades por dia da semana
    List<Disponibilidade> findByMedicoIdAndDiaSemana(Long medicoId, String diaSemana);
    
    // Consulta personalizada para verificar disponibilidade em um horário específico
    @Query("SELECT d FROM Disponibilidade d WHERE " +
           "d.medico.id = :medicoId AND " +
           "d.diaSemana = :diaSemana AND " +
           ":horario BETWEEN d.horarioInicio AND d.horarioFim")
    List<Disponibilidade> findDisponibilidadeNoHorario(
            @Param("medicoId") Long medicoId,
            @Param("diaSemana") String diaSemana,
            @Param("horario") LocalTime horario);
    
    // Verifica se existe conflito de horários para um médico
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
           "FROM Disponibilidade d WHERE " +
           "d.medico.id = :medicoId AND " +
           "d.diaSemana = :diaSemana AND " +
           "((d.horarioInicio < :horarioFim AND d.horarioFim > :horarioInicio))")
    boolean existsConflitoDeHorario(
            @Param("medicoId") Long medicoId,
            @Param("diaSemana") String diaSemana,
            @Param("horarioInicio") LocalTime horarioInicio,
            @Param("horarioFim") LocalTime horarioFim);
}