package com.kubra.todolist.mapper;

import com.kubra.todolist.model.User;
import com.kubra.todolist.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testUserToUserResponse() {
        User user = User.builder()
                .id("u1")
                .name("Kübra")
                .surname("Mülayim")
                .email("kubra@example.com")
                .active(true)
                .build();

        UserResponse response = userMapper.userToUserResponse(user);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("u1");
        assertThat(response.getName()).isEqualTo("Kübra");
        assertThat(response.getSurname()).isEqualTo("Mülayim");
        assertThat(response.getEmail()).isEqualTo("kubra@example.com");
        assertThat(response.isActive()).isTrue();
    }

    @Test
    void testUsersToUserResponses() {
        User user1 = User.builder().id("1").name("Ali").email("ali@mail.com").build();
        User user2 = User.builder().id("2").name("Ayşe").email("ayse@mail.com").build();

        List<UserResponse> responses = userMapper.usersToUserResponses(List.of(user1, user2));

        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getName()).isEqualTo("Ali");
        assertThat(responses.get(1).getName()).isEqualTo("Ayşe");
    }
}