package jpaex;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Demo {

	static EntityManagerFactory emf;

	public static List<User> findAllAdminUsers() {
		final EntityManager em = emf.createEntityManager();
		final String jpql = "SELECT u FROM User u WHERE u.adminRights = true";
		final TypedQuery<User> query = em.createQuery(jpql, User.class);
		final List<User> results = query.getResultList();
		em.close();
		return results;
	}

	public static List<Thing> findAllThings() {
		final EntityManager em = emf.createEntityManager();
		final String jpql = "SELECT t FROM Thing t";
		final TypedQuery<Thing> query = em.createQuery(jpql, Thing.class);
		final List<Thing> results = query.getResultList();
		em.close();
		return results;
	}

	public static List<Thing> findAllThingsForUser(final User user) {
		final EntityManager em = emf.createEntityManager();
		final String jpql = "SELECT t FROM Thing t WHERE t.user = :user";
		final TypedQuery<Thing> query = em.createQuery(jpql, Thing.class);
		query.setParameter("user", user);
		final List<Thing> results = query.getResultList();
		em.close();
		return results;
	}

	public static List<String> findAllUsernames() {
		final EntityManager em = emf.createEntityManager();
		final String jpql = "SELECT u.username FROM User u";
		final TypedQuery<String> query = em.createQuery(jpql, String.class);
		final List<String> results = query.getResultList();
		em.close();
		return results;
	}

	public static List<User> findAllUsers() {
		final EntityManager em = emf.createEntityManager();
		final String jpql = "SELECT u FROM User u";
		final TypedQuery<User> query = em.createQuery(jpql, User.class);
		final List<User> results = query.getResultList();
		em.close();
		return results;
	}

	public static Optional<User> findByThing(final Thing thing) {
		Optional<User> foundUser = Optional.empty();
		final EntityManager em = emf.createEntityManager();
		final String jpql = "SELECT t.user FROM Thing t WHERE t = :thing";
		final TypedQuery<User> query = em.createQuery(jpql, User.class);
		query.setParameter("thing", thing);
		final List<User> results = query.getResultList();
		if (!results.isEmpty())
			foundUser = Optional.of(results.get(0));
		em.close();
		return foundUser;
	}

	public static Optional<User> findByUsername(final String username) {
		Optional<User> foundUser = Optional.empty();
		final EntityManager em = emf.createEntityManager();
		final TypedQuery<User> query = em.createNamedQuery("findByUsername", User.class);
		query.setParameter("name", username);
		final List<User> results = query.getResultList();
		if (!results.isEmpty())
			foundUser = Optional.of(results.get(0));
		em.close();
		return foundUser;
	}

	public static void main(final String[] args) {

		emf = Persistence.createEntityManagerFactory("JPA");

		EntityManager em = emf.createEntityManager();

		User user1 = new User("Captain Jack Sparrow", false);
		Thing thing1 = new Thing();
		Thing thing2 = new Thing();

		em.getTransaction().begin();

		// Order of operations With Cascading:
		user1.addThing(thing1);
		user1.addThing(thing2);
		user1 = em.merge(user1);
		thing1 = user1.getThings().get(0);
		thing1.setUser(user1);
		thing2 = user1.getThings().get(1);
		thing2.setUser(user1);

		// Order of operations Without Cascading:
		// thing1 = em.merge(thing1);
		// thing2 = em.merge(thing2);
		// user1.addThing(thing1);
		// user1.addThing(thing2);
		// user1 = em.merge(user1);
		// thing1.setUser(user1);
		// thing2.setUser(user1);

		em.getTransaction().commit();

		em.close();

		em = emf.createEntityManager();

		final List<User> userresults = findAllUsers();
		System.out.println(userresults);

		final List<Thing> thingresults = findAllThingsForUser(user1);
		System.out.println(thingresults);

		final Optional<User> foundUser = findByThing(thing1);
		if (foundUser.isPresent())
			System.out.println(foundUser.get());

		em.close();

		emf.close();

	}

}
