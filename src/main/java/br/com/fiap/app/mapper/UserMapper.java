package br.com.fiap.app.mapper;

import br.com.fiap.app.model.User;
import br.com.fiap.app.request.UserPostRequest;
import br.com.fiap.app.response.UserGetResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserGetResponse userToGetResponse(User user);

    List<UserGetResponse> usersToGetResponseList(List<User> users);

    User postToUser(UserPostRequest request);
}
