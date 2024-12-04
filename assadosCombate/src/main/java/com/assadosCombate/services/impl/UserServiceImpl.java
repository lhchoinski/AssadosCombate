package com.assadosCombate.services.impl;

import com.assadosCombate.dtos.entities.UserDTO;
import com.assadosCombate.dtos.select2.Select2OptionsDTO;
import com.assadosCombate.entities.User;
import com.assadosCombate.exeptions.BadRequestException;
import com.assadosCombate.mappers.UserMapper;
import com.assadosCombate.repositories.UserRepository;
import com.assadosCombate.dtos.response.PageDTO;
import com.assadosCombate.services.Specification.UserSpecification;
import com.assadosCombate.services.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public PageDTO<User, UserDTO> findAll(int pageNo, int pageSize, String search, Boolean status) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("nome"));

        Specification<User> specification = UserSpecification.getFilters(search, status);

        Page<User> analistaPage = userRepository.findAll(specification, pageable);

        return new PageDTO<>(analistaPage, userMapper::toDto);
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        if (userRepository.findByLogin(userDTO.getLogin()) != null) {
            throw new BadRequestException("Login em uso");
        }

        if (userDTO.getNome() != null) {
            userDTO.setNome(userDTO.getNome().toUpperCase());
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());

        User user = userMapper.toEntity(userDTO);
        user.setPassword(encryptedPassword);
        user.setStatus(true);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDTO update(UserDTO userDTO) throws BadRequestException {
        User user = getUsuario(userDTO.getId());

        if (userDTO.getNome() != null) {
            userDTO.setNome(userDTO.getNome().toUpperCase());
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());

        user = userMapper.partialUpdate(userDTO, user);
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserDTO findById(UUID id) throws BadRequestException {
        User user = getUsuario(id);

        return userMapper.toDto(user);
    }

    @Override
    public void delete(UUID id) throws BadRequestException {
        User user = getUsuario(id);
        if(!user.getStatus()){
            userRepository.delete(user);
        }
        throw new BadRequestException("Usuário ainda ativo");


    }

    @Override
    public void changeStatus(UUID id, boolean status) {
        Optional<User> optionalUsuario = this.userRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            User user = optionalUsuario.get();
            user.setStatus(status);

            this.userRepository.save(user);
        }
    }

    @Override
    public List<Select2OptionsDTO> findAllToSelect2(String searchTerm) {

        List<User> list;

        if (StringUtils.isBlank(searchTerm)) {
            list = userRepository.findAllByStatusIsTrue();
        } else {
            list = userRepository.findByStatusIsTrueAndNomeContainingIgnoreCaseOrderByNome(searchTerm);
        }

        return list
                .stream()
                .map(data -> new Select2OptionsDTO(data.getId(), data.getNome()))
                .collect(Collectors.toList());

    }

    private User getUsuario(UUID id) throws BadRequestException {
        return userRepository.findById(id).orElseThrow(()
                -> new BadRequestException("Usuário com o ID: " + id + " não encontrado"));
    }

}


