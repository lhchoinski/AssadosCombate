package com.assadosCombate.mappers;

import com.assadosCombate.dtos.entities.UserDTO;
import com.assadosCombate.entities.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);

    List<UserDTO> toDto(List<User> user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDTO userDTO, @MappingTarget User user);
}
