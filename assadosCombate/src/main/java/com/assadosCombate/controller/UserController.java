package com.assadosCombate.controller;

import com.assadosCombate.dtos.entities.UserDTO;
import com.assadosCombate.dtos.groups.AppGroup;
import com.assadosCombate.dtos.select2.Select2OptionsDTO;
import com.assadosCombate.entities.User;
import com.assadosCombate.exeptions.BadRequestException;
import com.assadosCombate.dtos.response.PageDTO;
import com.assadosCombate.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Usuário")
@RestController
@RequestMapping("${api-prefix}/administrador/usuario")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Listar", description = "Listar")
    @JsonView(AppGroup.ResponsePage.class)
    public ResponseEntity<PageDTO<User, UserDTO>> findAll(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "status", required = false) Boolean status
    ) {
        return ResponseEntity.ok(userService.findAll(pageNo, pageSize, search, status));
    }

    @Operation(summary = "Pesquisar", description = "Pesquisar pessoas para Select2", tags = {"Select2"})
    @GetMapping("/select2")
    public ResponseEntity<List<Select2OptionsDTO>> findAllToSelect2(
            @RequestParam(name = "q", required = false, defaultValue = "") String searchTerm
    ) {
        return ResponseEntity.ok(userService.findAllToSelect2(searchTerm));
    }

    @GetMapping("/{id}")
    @JsonView(AppGroup.ResponsePage.class)
    public ResponseEntity<UserDTO> findById(@PathVariable("id") UUID id) throws BadRequestException {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Ativar", description = "Ativar pessoa")
    @PutMapping({"/{id}/ativar"})
    public ResponseEntity<?> enable(@PathVariable("id") UUID id) {
        this.userService.changeStatus(id, true);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Desativar", description = "Desativar pessoa")
    @PutMapping({"/{id}/desativar"})
    public ResponseEntity<?> disable(@PathVariable("id") UUID id) {
        this.userService.changeStatus(id, false);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Cadastrar", description = "Cadastrar")
    @JsonView(AppGroup.Response.class)
    @PostMapping
    public ResponseEntity<UserDTO> create(
            @Validated(AppGroup.Request.class)
            @RequestBody
            @JsonView(AppGroup.Request.class)
            UserDTO userDTO
    ) {
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @Operation(summary = "Editar", description = "Editar")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable("id") UUID id,
            @Validated(AppGroup.Request.class)
            @RequestBody
            @JsonView(AppGroup.Request.class)
            UserDTO userDTO
    ) throws BadRequestException {
        userDTO.setId(id);

        return ResponseEntity.ok(userService.update(userDTO));
    }

    @Operation(summary = "Deletar", description = "Deletar")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) throws BadRequestException {
        userService.delete(id);

        return ResponseEntity.ok().build();
    }
}
