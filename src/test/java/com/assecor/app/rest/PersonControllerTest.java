package com.assecor.app.rest;

import com.assecor.app.entity.Person;
import com.assecor.app.exception.PersonDataMissingException;
import com.assecor.app.exception.PersonNotFoundException;
import com.assecor.app.repository.PersonRepository;
import com.assecor.app.repository.SimplePersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@SpringBootTest
public class PersonControllerTest
{
	PersonRepository repository;
	PersonController personController;

	@Before
	public void init() {
		repository = new SimplePersonRepository();
		personController = new PersonController(repository);
	}

	/* getAllPersons() */
	@Test
	public void testGetAllEmptyList() {
		assertEquals(0, personController.getAllPersons().size());
	}

	@Test
	public void testGetAllFoundTwoPersons() {
		Person johnDoe = new Person((long)1, "Doe", "John", 12345, "Berlin", "blau");
		Person jackBlack = new Person((long)2, "Black", "Jack", 54321, "Frankfurt", "rot");
		repository.save(johnDoe);
		repository.save(jackBlack);
		assertEquals(2, personController.getAllPersons().size());
	}

	/* getOneById() */
	@Test
	public void testGetOnePersonById() {
		Person johnDoe = new Person((long)1, "Doe", "John", 12345, "Berlin", "blau");
		repository.save(johnDoe);
		assertThat(johnDoe).isEqualTo(personController.getOneById((long)1));
	}

	@Test(expected = PersonNotFoundException.class)
	public void testGetOnePersonNotFound() {
		Person johnDoe = new Person((long)1, "Doe", "John", 12345, "Berlin", "blau");
		repository.save(johnDoe);
		assertThat(johnDoe).isEqualTo(personController.getOneById((long)2));
	}

	/* getPersonsByColor() */
	@Test
	public void testGetPersonColorBlauExist() {
		Person johnDoe = new Person((long)1, "Doe", "John", 12345, "Berlin", "blau");
		Person jackBlack = new Person((long)2, "Black", "Jack", 54321, "Frankfurt", "blau");
		repository.save(johnDoe);
		repository.save(jackBlack);
		assertEquals(2, personController.getPersonsByColor(1).size());
		assertTrue(personController.getPersonsByColor(1).contains(johnDoe) &&
							personController.getPersonsByColor(1).contains(jackBlack));
	}

	@Test
	public void testGetPersonColorRotNotExist() {
		Person johnDoe = new Person((long)1, "Doe", "John", 12345, "Berlin", "blau");
		Person jackBlack = new Person((long)2, "Black", "Jack", 54321, "Frankfurt", "blau");
		repository.save(johnDoe);
		repository.save(jackBlack);
		assertEquals(0, personController.getPersonsByColor(4).size());
		assertFalse(personController.getPersonsByColor(1).contains(johnDoe) ||
							 personController.getPersonsByColor(1).contains(jackBlack));
	}

	/* saveOne() */
	@Test
	public void testAddPersonSuccessful() {
		Person johnDoe = new Person((long)1, "Doe", "John", 12345, "Berlin", "blau");
		personController.saveOne(johnDoe);
		assertThat(johnDoe).isEqualTo(personController.getOneById(johnDoe.getId()));
	}

	@Test(expected = PersonDataMissingException.class)
	public void testAddPersonNMissingData() {
		Person invalidPerson = new Person((long)1, "", "", 12345, "Berlin", "blau");
		personController.saveOne(invalidPerson);
	}
}
