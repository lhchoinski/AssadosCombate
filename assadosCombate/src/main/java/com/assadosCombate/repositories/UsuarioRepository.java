package com.assadosCombate.repositories;

import com.assadosCombate.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID>, JpaSpecificationExecutor<Usuario> {
    Usuario findByLogin(String login);
    List<Usuario> findAllByStatusIsTrue();
    List<Usuario> findByStatusIsTrueAndNomeContainingIgnoreCaseOrderByNome(String nome);
}