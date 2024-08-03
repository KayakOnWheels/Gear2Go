package com.gear2go.mapper;

import com.gear2go.domain.dto.response.UserResponse;
import com.gear2go.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMail()
        );
    }

    public List<UserResponse> toUserResponseList(List<User> user) {
        return user.stream()
                .map(this::toUserResponse)
                .toList();
    }
}
