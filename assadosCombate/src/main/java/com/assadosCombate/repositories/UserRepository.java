package com.assadosCombate.repositories;

import com.assadosCombate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    User findByLogin(String login);
    List<User> findAllByStatusIsTrue();
    List<User> findByStatusIsTrueAndNomeContainingIgnoreCaseOrderByNome(String nome);
}