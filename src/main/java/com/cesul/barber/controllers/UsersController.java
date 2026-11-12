package com.cesul.barber.controllers;

import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Gerenciamento de usuários da barbearia")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Retorna a lista de todos os usuários cadastrados.")
    public ResponseEntity<List<UserCreationDTO>> getAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Retorna um usuário específico pelo seu ID.")
    public ResponseEntity<UserCreationDTO> getById(@PathVariable UUID id) {
        Optional<UserCreationDTO> userOptional = this.userService.findById(id);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário na barbearia.")
    public ResponseEntity<UserCreationDTO> create(@RequestBody UserCreationDTO user) {
        UserCreationDTO newUser = this.userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza as informações de um usuário existente.")
    public ResponseEntity<UserCreationDTO> update(@PathVariable UUID id, @RequestBody UserCreationDTO updatedUser) {
        Optional<UserCreationDTO> userOptional = this.userService.update(id, updatedUser);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/avatar")
    @Operation(summary = "Atualizar avatar do usuário", description = "Atualiza o avatar de um usuário existente.")
    public ResponseEntity<UserCreationDTO> patchUser(@PathVariable UUID id, @RequestBody Map<String, String> avatar) {
        String avatarUrl = avatar.get("avatarUrl");
        Optional<UserCreationDTO> userOptional = this.userService.updateAvatar(id, avatarUrl);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Exclui um usuário existente.")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean removed = this.userService.delete(id);

        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Limpar usuários", description = "Remove todos os usuários cadastrados.")
    public ResponseEntity<Void> clearUsers() {
        this.userService.clear();
        return ResponseEntity.noContent().build();
    }
}