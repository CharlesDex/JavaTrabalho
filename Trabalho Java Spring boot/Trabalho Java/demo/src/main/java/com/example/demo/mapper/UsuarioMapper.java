package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Pacientes;
import com.example.demo.dto.UsuarioDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    

    UsuarioDTO toDTO(Pacientes usuario);

    Pacientes toEntity(UsuarioDTO usuarioDTO);

    List<UsuarioDTO> toDTOList(List<Pacientes> usuarios);
}
