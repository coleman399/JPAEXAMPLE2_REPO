package jpaex;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Thing {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne()
	private User user;

	Thing() {
	}

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Thing [id=" + id + ", user=" + user.getId() + "]";
	}

}
