package com.cesul.barber.services;

import com.cesul.barber.dtos.UserCreationDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final List<UserCreationDTO> users = new ArrayList<>();

    public UserCreationDTO create(UserCreationDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }
        user.setId(UUID.randomUUID());
        this.users.add(user);
        return user;
    }

    public List<UserCreationDTO> findAll() {
        return new ArrayList<>(this.users);
    }

    public Optional<UserCreationDTO> findById(UUID id) {
        return this.users.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst();
    }

    public Optional<UserCreationDTO> update(UUID id, UserCreationDTO updatedUser) {
        if (updatedUser == null) {
            return Optional.empty();
        }

        for (int i = 0; i < users.size(); i++) {
            if (Objects.equals(users.get(i).getId(), id)) {
                updatedUser.setId(id);
                users.set(i, updatedUser);
                return Optional.of(updatedUser);
            }
        }
        return Optional.empty();
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, String avatarUrl) {
        Optional<UserCreationDTO> userOptional = findById(id);
        if (userOptional.isPresent()) {
            userOptional.get().setAvatarUrl(avatarUrl);
        }
        return userOptional;
    }

    public boolean delete(UUID id) {
        return this.users.removeIf(u -> Objects.equals(u.getId(), id));
    }

    public void clear() {
        this.users.clear();
    }
}