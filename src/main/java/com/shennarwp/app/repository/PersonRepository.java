package com.shennarwp.app.repository;

import com.shennarwp.app.entity.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/* provide generic interface for another future repository implementation */
@Repository
public interface PersonRepository
{
	Person save(Person toBeSaved);
	List<Person> findAll();
	Optional<Person> findById(Long id);
	List<Person> findByColor(int color);
}