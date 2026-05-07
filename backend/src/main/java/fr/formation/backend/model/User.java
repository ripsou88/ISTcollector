package fr.formation.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.ColumnDefault;

@Entity
@DiscriminatorValue("usr")
public class User extends Compte {

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "user-card",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "ist_id"))
  private List<Ist> ists = new ArrayList<>();

  @Column(name = "level")
  @ColumnDefault("0")
  private Integer Level = 0;

  public User() {}

  public User(Integer id, String username, String password, List<Ist> ists) {
    super(id, username, password);
    this.ists = ists;
  }

  public List<Ist> getIsts() {
    return ists;
  }

  public void setIsts(List<Ist> ists) {
    this.ists = ists;
  }

  public Integer getLevel() {
    return Level;
  }

  public void setLevel(Integer level) {
    Level = level;
  }
}
