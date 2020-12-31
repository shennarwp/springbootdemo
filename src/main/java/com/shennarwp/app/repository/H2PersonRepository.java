package com.shennarwp.app.repository;

import com.shennarwp.app.entity.Color;
import com.shennarwp.app.entity.Person;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
	private EntityManagerFactory factory;
	EntityManager entityManager;
	EntityTransaction transaction;

	public H2PersonRepository(){}

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
