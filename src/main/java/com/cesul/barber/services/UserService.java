package com.cesul.barber.services;

import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.entities.User;
import com.cesul.barber.mappers.UserMapper;
import com.cesul.barber.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserCreationDTO create(UserCreationDTO userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }
        User user = this.userMapper.toEntity(userDto);
        User saved = this.userRepository.save(user);
        return this.userMapper.toDTO(saved);
    }

    public List<UserCreationDTO> findAll() {
        return this.userRepository.findAll().stream()
                .map(this.userMapper::toDTO)
                .toList();
    }

    public Optional<UserCreationDTO> findById(UUID id) {
        return this.userRepository.findById(id)
                .map(this.userMapper::toDTO);
    }

    public Optional<UserCreationDTO> update(UUID id, UserCreationDTO updatedUser) {
        if (updatedUser == null) {
            return Optional.empty();
        }

        return this.userRepository.findById(id).map(existing -> {
            existing.setName(updatedUser.getName());
            existing.setEmail(updatedUser.getEmail());
            existing.setAvatarUrl(updatedUser.getAvatarUrl());
            User saved = this.userRepository.save(existing);
            return this.userMapper.toDTO(saved);
        });
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, String avatarUrl) {
        return this.userRepository.findById(id).map(existing -> {
            existing.setAvatarUrl(avatarUrl);
            User saved = this.userRepository.save(existing);
            return this.userMapper.toDTO(saved);
        });
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, UserCreationDTO userDto) {
        if (userDto == null) {
            return Optional.empty();
        }
        return updateAvatar(id, userDto.getAvatarUrl());
    }

    public boolean delete(UUID id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void clear() {
        this.userRepository.deleteAll();
    }
}
