package com.assecor.app.repository;

import com.assecor.app.entity.Color;
import com.assecor.app.entity.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Basic JPA-Repository implementation, used with H2 in-memory DB
 * WILL generate id like normal DB
 * will be injected if value in application.properties:
 * spring.profiles.active=h2
 */
@Repository
@Profile("h2")
public class H2PersonRepository implements PersonRepository
{
	/* these will be read from application.properties*/
	@Value( "${spring.datasource.url}" )
	private String url;

	@Value( "${spring.datasource.username}")
	private String username;

	@Value( "${spring.datasource.password}")
	private String password;

	private Connection connection;
	private EntityManagerFactory factory;
	EntityManager entityManager;
	EntityTransaction transaction;

	public H2PersonRepository() {
		try {
			connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public void initTransaction() {
		factory = Persistence.createEntityManagerFactory("h2");
		entityManager = factory.createEntityManager();
		transaction = entityManager.getTransaction();
	}

	public void finishTransaction() {
		entityManager.close();
		factory.close();
	}

	/* save a person */
	@Override
	public Person save(Person toBeSaved) {
		initTransaction();
		transaction.begin();

		entityManager.persist(toBeSaved);
		transaction.commit();

		finishTransaction();
		return toBeSaved;
	}

	/* return all the persons in the database */
	@Override
	public List<Person> findAll() {
		initTransaction();
		transaction.begin();

		List<Person> personList = entityManager.createQuery("SELECT p FROM Person p", Person.class)
				.getResultList();

		finishTransaction();
		return personList;
	}

	/* return person with specific id */
	@Override
	public Optional<Person> findById(Long id) {
		initTransaction();
		transaction.begin();

		Person person = entityManager.find(Person.class, id);
		finishTransaction();

		return Optional.of(person);
	}

	/* return all persons having the same color */
	@Override
	public List<Person> findByColor(int color) {
		return findAll().stream().filter(person -> person.getColor().
				equals(Color.pickColor(color)))
				.collect(Collectors.toList());
	}
}
