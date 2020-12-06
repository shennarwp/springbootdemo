package com.assecor.app.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * This class hold the person object
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Person
{
	@Id @NonNull @GeneratedValue
	private Long id;

	@NonNull
	private String lastname;

	@NonNull
	private String firstName;

	@NonNull
	private int zipcode;

	@NonNull
	private String city;

	@NonNull
	private String color;

	@Override
	public boolean equals(Object o) {

		if (o == this) return true;
		if (!(o instanceof Person)) {
			return false;
		}
		Person person = (Person) o;
		return id.equals(person.getId()) &&
				Objects.equals(lastname, person.getLastname()) &&
				Objects.equals(firstName, person.getFirstName()) &&
				zipcode == person.getZipcode() &&
				Objects.equals(city, person.getCity()) &&
				Objects.equals(color, person.getColor());
	}

	@Override
	public String toString() {
		return "id: " + id +
				", lastname: " + lastname +
				", firstname: " + firstName +
				", zipcode: " + zipcode +
				", city: " + city +
				", color: " + color;
	}
}
