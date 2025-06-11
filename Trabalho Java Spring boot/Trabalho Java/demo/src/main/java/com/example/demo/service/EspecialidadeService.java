package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Especialidade;
import com.example.demo.Entities.Medico;
import com.example.demo.dto.EspecialidadeDTO;
import com.example.demo.mapper.EspecialidadeMapper;
import com.example.demo.repository.EspecialidadeRepository;
import com.example.demo.repository.MedicoRepository;


@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadeMapper especialidadeMapper;

    public List<EspecialidadeDTO> listarTodos() {
        return especialidadeMapper.toDTOList(especialidadeRepository.findAll());
    }

    public Optional<EspecialidadeDTO> buscarPorId(Long id) {
        Optional<EspecialidadeDTO> especialidadeDTO = especialidadeRepository.findById(id)
                .map(especialidadeMapper::toDTO);
        return especialidadeDTO;

    }

    public EspecialidadeDTO salvar(EspecialidadeDTO especialidadeDTO) {
        Especialidade especialidade = especialidadeMapper.toEntity(especialidadeDTO);
        return especialidadeMapper.toDTO(especialidadeRepository.save(especialidade));
    }

    public EspecialidadeDTO atualizar(Long id, EspecialidadeDTO especialidadeDTO) {
        return especialidadeRepository.findById(id).map(especialidade -> {
            especialidade.setNome(especialidadeDTO.getNome());
            especialidadeRepository.save(especialidade);
            return especialidadeMapper.toDTO(especialidade);
        }).orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada com o ID: " + id));
    }

    public EspecialidadeDTO deletar(Long id) {
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDTO(especialidadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada com o ID: " + id)));
        List<Medico> medicos = medicoRepository.findByEspecialidadeId(id);
        if (!medicos.isEmpty()) {
            throw new IllegalArgumentException(
                    "Esta especialidade não pode ser deletada pois existem médicos com essa especialidade cadastrados!");
        }

        especialidadeRepository.deleteById(id);
        return especialidadeDTO;

    }
}