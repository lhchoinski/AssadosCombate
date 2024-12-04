package com.assadosCombate.services;

import com.assadosCombate.dtos.entities.UserDTO;
import com.assadosCombate.dtos.select2.Select2OptionsDTO;
import com.assadosCombate.entities.User;
import com.assadosCombate.exeptions.BadRequestException;
import com.assadosCombate.dtos.response.PageDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    PageDTO<User, UserDTO> findAll(int pageNo, int pageSize, String search, Boolean status);
    UserDTO create(UserDTO userDTO);
    UserDTO update(UserDTO userDTO) throws BadRequestException;
    UserDTO findById(UUID id) throws BadRequestException;
    void delete(UUID id) throws BadRequestException;
    void changeStatus(UUID var1, boolean var2);
    List<Select2OptionsDTO> findAllToSelect2(String searchTerm);
}
