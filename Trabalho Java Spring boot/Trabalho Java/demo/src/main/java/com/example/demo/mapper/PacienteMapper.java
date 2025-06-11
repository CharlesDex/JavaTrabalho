package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.Entities.Pacientes;
import com.example.demo.dto.PacienteDTO;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    PacienteDTO toDTO(Pacientes paciente);

    @Mapping(target = "consultas", ignore = true)
    Pacientes toEntity(PacienteDTO pacienteDTO);

    List<PacienteDTO> toDTOList(List<Pacientes> pacientes);
}