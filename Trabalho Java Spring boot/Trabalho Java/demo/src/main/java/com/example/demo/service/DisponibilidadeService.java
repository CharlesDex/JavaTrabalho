package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Disponibilidade;
import com.example.demo.Entities.Medico;
import com.example.demo.dto.DisponibilidadeDTO;
import com.example.demo.mapper.DisponibilidadeMapper;
import com.example.demo.repository.DisponibilidadeRepository;
import com.example.demo.repository.MedicoRepository;

@Service
public class DisponibilidadeService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private DisponibilidadeMapper disponibilidadeMapper;

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

public DisponibilidadeDTO registrarDisponibilidade(Long medicoId, DisponibilidadeDTO disponibilidadeDTO) {
    // Buscar o médico do banco de dados
    Medico medico = medicoRepository.findById(medicoId)
        .orElseThrow(() -> new IllegalArgumentException("Médico com ID " + medicoId + " não encontrado"));

    // Montar a disponibilidade manualmente
    Disponibilidade disponibilidade = new Disponibilidade();
    disponibilidade.setDiaDaSemana(disponibilidadeDTO.getDiaDaSemana());
    disponibilidade.setHorarioInicio(disponibilidadeDTO.getHorarioInicio());
    disponibilidade.setHorarioFim(disponibilidadeDTO.getHorarioFim());
    disponibilidade.setMedico(medico); // importante: entidade gerenciada!

    // Salvar e retornar DTO
    Disponibilidade salva = disponibilidadeRepository.save(disponibilidade);
    return disponibilidadeMapper.toDTO(salva);
}

    public List<DisponibilidadeDTO> listarDisponibilidade(Long medicoId) {
        return disponibilidadeMapper.toDTOList(disponibilidadeRepository.findByMedicoId(medicoId));
    }

    public void removerDisponibilidade(Long Id) {
        disponibilidadeRepository.deleteById(Id);
    }
}