package com.kubra.todolist.mapper;

import com.kubra.todolist.model.User;
import com.kubra.todolist.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    List<UserResponse> usersToUserResponses(List<User> users);

}
