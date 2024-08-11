package com.gear2go.service;

import com.gear2go.dto.request.user.CreateUserRequest;
import com.gear2go.dto.request.user.UpdateUserRequest;
import com.gear2go.dto.response.UserResponse;
import com.gear2go.entity.User;
import com.gear2go.entity.enums.Role;
import com.gear2go.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTestSuite {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AuthenticationService authenticationService;


    List<User> prepareData() {
        return List.of(
                new User("Antonio", "Antonello", "aa@mail.com", "pass123", Role.ADMIN),
                new User("Lina", "Linetti", "ll@mail.com", "pass123", Role.USER),
                new User("Joseph", "Troc", "jt@mail.com", "pass123", Role.GUEST)
        );
    }


    @Test
    void shouldReturnUserResponseList() {
        //Given
        List<User> userList = prepareData();
        when(userRepository.findAll()).thenReturn(userList);

        //When
        List<UserResponse> resultList = userService.getAllUsers();

        //Then
        assertEquals(3, resultList.size());
        assertEquals("Lina", resultList.get(1).firstName());
        assertEquals("Troc", resultList.get(2).lastName());
    }

    @Test
    void shouldCreateNewUserAndReturnUserResponse() {
        //Given
        CreateUserRequest request = new CreateUserRequest("Antonio", "Antonello", "aa@mail.com", "pass123");

        //When
        UserResponse result = userService.createUser(request);

        //Then
        assertEquals("Antonio", result.firstName());
        assertEquals("Antonello", result.lastName());
    }

    @Test
    void shouldUpdateUserAndReturnUserResponse() throws Exception {
        //Given
        User user = new User("Antonio", "Antonello", "aa@mail.com", "pass123", Role.ADMIN);
        UpdateUserRequest request = new UpdateUserRequest(2L, "Liza", "Leroy", "aa@mail.com", "pass123");

        when(authenticationService.getAuthenticatedUser()).thenReturn(Optional.of(user));

        //When
        UserResponse result = userService.updateUser(request);

        //Then
        assertEquals("Liza", result.firstName());
        assertEquals("Leroy", result.lastName());
        assertEquals("aa@mail.com", result.mail());
    }

}
