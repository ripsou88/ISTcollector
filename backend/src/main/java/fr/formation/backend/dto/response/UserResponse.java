package fr.formation.backend.dto.response;

import fr.formation.backend.model.Ist;
import fr.formation.backend.model.User;
import java.util.List;

public record UserResponse(Integer id, String username, Integer level, List<Ist> ist) {

  public static UserResponse convert(User user) {
    return new UserResponse(user.getId(), user.getUsername(), user.getLevel(), user.getIsts());
  }
}
