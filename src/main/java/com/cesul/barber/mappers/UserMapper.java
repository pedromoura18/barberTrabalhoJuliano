package com.cesul.barber.mappers;

import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserCreationDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserCreationDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatarUrl()
        );
    }

    public User toEntity(UserCreationDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAvatarUrl(dto.getAvatarUrl());
        return user;
    }
}
