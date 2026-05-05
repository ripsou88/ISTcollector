package fr.formation.backend.dto.response;

import fr.formation.backend.model.User;

public record UserResponse(Integer id, String username, String password) {

    public static UserResponse convert(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword());
    }

}
