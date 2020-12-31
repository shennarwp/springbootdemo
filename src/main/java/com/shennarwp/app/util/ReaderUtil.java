package com.shennarwp.app.util;

import com.shennarwp.app.entity.Person;

import java.util.List;

/* provide generic interface for another future implementation */
public interface ReaderUtil
{
	List<String> readData(String filePath);
	List<Person> extractPerson(List<String> dataFromCSV);
}
