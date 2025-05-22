package com.example.demo.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Disponibilidade;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {

    // Lista todas as disponibilidades de um médico
       List<Disponibilidade> findByMedicoId(Long medicoId);
   
       // Lista disponibilidades de um médico em um dia da semana específico
       List<Disponibilidade> findByMedicoIdAndDiaSemana(Long medicoId, String diaSemana);
   
       // Verifica se há disponibilidade para um horário específico (exato inicio)
       List<Disponibilidade> findByMedicoIdAndDiaSemanaAndHorarioinicio(Long medicoId, String diaSemana, LocalTime horarioinicio);
   
       // Verifica se existe conflito de horário com uma nova faixa de horário
       boolean existsByMedicoIdAndDiaSemanaAndHorarioinicioLessThanAndHorarioFimGreaterThan(
           Long medicoId,
           String diaSemana,
           LocalTime horaFim,
           LocalTime horaInicio
       );
   
   }
