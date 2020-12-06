package com.assecor.app.rest;

import com.assecor.app.entity.Person;
import com.assecor.app.exception.PersonDataMissingException;
import com.assecor.app.exception.PersonNotFoundException;
import com.assecor.app.repository.PersonRepository;
import com.sun.istack.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST-Controller for the application, responsible for managing the PersonRepository
 */
@RestController
public class PersonController
{
	/* will at runtime injected by Spring, based on @Profile annotation at Repository classes */
	private final PersonRepository repository;

	public PersonController(PersonRepository repository)
	{
		this.repository = repository;
	}

	/* GET return all available persons */
	@GetMapping("/persons")
	public List<Person> getAllPersons() {
		return new ArrayList<>(repository.findAll());
	}

	/* GET return one person with specific id */
	@GetMapping("/persons/{id}")
	public Person getOneById(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(id));
	}

	/* GET return all persons with specific color code */
	@GetMapping("/persons/color/{color}")
	public List<Person> getPersonsByColor(@PathVariable int color) {
		return repository.findByColor(color);
	}

	/* POST save new Person to the repository */
	@PostMapping("/persons")
	public Person saveOne(@NotNull @RequestBody Person newPerson) {
		if(!assertPersonDataValid(newPerson))
			throw new PersonDataMissingException();
		return repository.save(newPerson);
	}

	/* check if passed request to create new person contains all person's data necessary */
	private boolean assertPersonDataValid(Person person) {
		return person.getLastname() != null && !person.getLastname().isBlank() &&
				person.getFirstName() != null && !person.getFirstName().isBlank() &&
				person.getZipcode() != 0 &&
				person.getCity() != null && !person.getCity().isBlank() &&
				person.getColor() != null && !person.getColor().isBlank();
	}
}
