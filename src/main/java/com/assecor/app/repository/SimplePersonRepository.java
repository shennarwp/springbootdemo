package com.assecor.app.repository;

import com.assecor.app.entity.Color;
import com.assecor.app.entity.Person;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Basic / crude implementation of PersonRepository
 * only stored data in a list (not in the database)
 * WILL NOT generate id automatically like in real database
 * will be injected if value in application.properties:
 * spring.profiles.active=simple
 */
@Repository
@Profile("simple")
public class SimplePersonRepository implements PersonRepository
{
	/* this list will hold the data */
	private final List<Person> personList = new ArrayList<>();

	/* save a person, WILL NOT generate Person.Id */
	@Override
	public Person save(Person toBeSaved) {
		personList.add(toBeSaved);
		return toBeSaved;
	}

	/* return all the person */
	@Override
	public List<Person> findAll() {
		return personList;
	}

	/* return person by specified id */
	@Override
	public Optional<Person> findById(Long id) {
		return personList.stream()
				.filter(p->p.getId().equals(id))
				.findAny();
	}

	/* return list of persons having the same color */
	@Override
	public List<Person> findByColor(int color) {
		return personList.stream()
				.filter(person -> person.getColor().
						equals(Color.pickColor(color)))
				.collect(Collectors.toList());
	}
}
