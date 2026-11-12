package com.cesul.barber.services;

import com.cesul.barber.dtos.UserCreationDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private List<UserCreationDTO> users = new ArrayList<>();

    public UserCreationDTO create(UserCreationDTO user){
        user.setId(java.util.UUID.randomUUID());
        this.users.add(user);

        return user;
    }

    public List<UserCreationDTO> findAll(){
        return this.users;
    }

    public Optional<UserCreationDTO> findById(UUID id){
        return this.users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<UserCreationDTO> update(UUID id, Map<String, String> request){
        Optional<UserCreationDTO> userOptional = this.users.stream(
        ).filter(u -> u.getId().equals(id)).findFirst();

        return userOptional;
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, Map<String, String> request){
        Optional<UserCreationDTO> userOptional = this.users.stream(
        ).filter(u -> u.getId().equals(id)).findFirst();

        return userOptional;
    }

    public boolean detele(UUID id){
        return this.users.removeIf(u -> u.getId().equals(id));
    }

    public boolean clean(){
        this.users.clear();
        return true;
    }
}
