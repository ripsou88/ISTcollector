package fr.formation.backend.dto.response;

import java.util.List;

import fr.formation.backend.model.Ist;
import fr.formation.backend.model.User;

public record UserResponse(Integer id, String username, String password, List<Ist> ist ) {

    public static UserResponse convert(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getIst());
    }

}
