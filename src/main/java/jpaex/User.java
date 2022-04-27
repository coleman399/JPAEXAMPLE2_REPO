package jpaex;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQuery(name = "findByUsername", query = "SELECT u FROM User u WHERE u.username = :name")

@Entity
@Table(name = "MyUsers")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false, unique = true)
	private String username;

	private boolean adminRights;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Thing> things = new ArrayList<>();

	// JPA CONSTRUCTOR
	User() {
	}

	public User(final String username, final boolean adminRights) {
		this.username = username;
		this.adminRights = adminRights;
	}

	public void addThing(final Thing thing) {
		things.add(thing);
	}

	public long getId() {
		return id;
	}

	public List<Thing> getThings() {
		return things;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAdminRights() {
		return adminRights;
	}

	public void setAdminRights(final boolean adminRights) {
		this.adminRights = adminRights;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public void setThings(final List<Thing> things) {
		this.things = things;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", adminRights=" + adminRights + ", things=" + things
				+ "]";
	}

}
