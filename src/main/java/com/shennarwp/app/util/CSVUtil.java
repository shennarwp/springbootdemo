package com.shennarwp.app.util;

import com.shennarwp.app.entity.Color;
import com.shennarwp.app.entity.Person;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class to read from sample csv file
 * implement ReaderUtil - Interface
 */
public class CSVUtil implements ReaderUtil
{
	/**
	 * read csv file line by line,
	 * each line element will be splitted by ", " then represented as List of Strings
	 * 1 line -> 1 List<String>
	 * these list of List<String> will then be concatenated / flatten into one List<String>
	 * @return list of all String element from the csv file
	 */
	public List<String> readData(String filePath) {
		List<String> listOfLines;
		InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
		assert is != null;
		listOfLines = new BufferedReader(new InputStreamReader(is))
								.lines()
								.map(column -> column.split(", "))
								.map(Arrays::asList)
								.flatMap(List::stream)
								.map(String::trim)
								.collect(Collectors.toList());
		return listOfLines;
	}

	/**
	 * from the list produced above,
	 * create a new person object every 4 element (because the csv has 4 data columns)
	 * @param dataFromCSV list of all String element from the csv file
	 * @return List of Person objects created
	 */
	public List<Person> extractPerson(List<String> dataFromCSV) {
		List<Person> personList = new ArrayList<>();
		for (int i = 0; i < dataFromCSV.size(); i+=4) {

			/* 3rd element, split by space, first substring is zipcode, second one is city */
			String[] location = dataFromCSV.get(i+2).split(" ", 2);
			int zipcode = Integer.parseInt(location[0]);
			String city = location[1];

			Person person = new Person();
			person.setLastname(dataFromCSV.get(i));									// 1st column
			person.setFirstName(dataFromCSV.get(i+1));								// 2nd column
			person.setZipcode(zipcode);												// 3rd column
			person.setCity(city);													// 3rd column
			person.setColor(Color.pickColor(Integer.parseInt(dataFromCSV.get(i+3).trim())));	//4th column

			personList.add(person);
		}
		return personList;
	}
}
