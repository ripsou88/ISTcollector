package fr.formation.backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@DiscriminatorValue("usr")
public class User extends Compte {
	
	@ManyToMany
	@JoinTable(
	        name = "user-card",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "ist_id")
	    )
	private List<Ist> ist = new ArrayList<>();
	
    public User() {
    }

    public User(Integer id, String username, String password,List<Ist> ist) {
        super(id, username, password);
        this.ist=ist;
    }

	public List<Ist> getIst() {
		return ist;
	}

	public void setIst(List<Ist> ist) {
		this.ist = ist;
	}
    
    
    

}
