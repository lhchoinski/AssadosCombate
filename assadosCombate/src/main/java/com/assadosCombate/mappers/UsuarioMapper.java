package com.assadosCombate.mappers;

import com.assadosCombate.dtos.entities.UsuarioDTO;
import com.assadosCombate.entities.Usuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO usuarioDTO);

    UsuarioDTO toDto(Usuario usuario);

    List<UsuarioDTO> toDto(List<Usuario> usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate(UsuarioDTO usuarioDTO, @MappingTarget Usuario usuario);
}
