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

    private List<UserCreationDTO> users = new ArrayList<>();

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    @Operation(summary = "Listar todos", description = "Retorna a lista de todos os usuários cadastrados.")
    public ResponseEntity<List<UserCreationDTO>> getAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza as informações de um usuário existente.")
    public ResponseEntity<UserCreationDTO> update(@PathVariable UUID id, @RequestBody UserCreationDTO updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                updatedUser.setId(id);
                users.set(i, updatedUser);
                return ResponseEntity.ok(updatedUser);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário na barbearia.")
    public ResponseEntity<UserCreationDTO> create(@RequestBody UserCreationDTO user) {
        UserCreationDTO newUser = this.userService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PatchMapping("/{id}/avatar")
    @Operation(summary = "Atualizar avatar do usuário", description = "Atualiza o avatar de um usuário existente.")
    public ResponseEntity<UserCreationDTO> patchUser(@PathVariable UUID id, @RequestBody Map<String, Object> avatar) {
        Optional<UserCreationDTO> userOptional = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserCreationDTO existingUser = userOptional.get();

        if (avatar.containsKey("avatarUrl")) {
            existingUser.setAvatarUrl((String) avatar.get("avatarUrl"));
        }

        return ResponseEntity.ok(existingUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Exclui um usuário existente.")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean removed = users.removeIf(u -> u.getId().equals(id));

        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Limpar usuários", description = "Remove todos os usuários cadastrados.")
    public ResponseEntity<Void> clearUsers() {
        this.users.clear();
        return ResponseEntity.noContent().build();
    }
}
