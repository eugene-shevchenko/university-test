package com.university.service;

import com.university.domain.Person;
import com.university.domain.Phrase;

import java.util.Collection;
import java.util.List;

/**
 * Created by eshevchenko on 03.03.15.
 */
public interface TeacherService extends PhilosopherService {

    List<Person> getAllTeachers();

    Person getTeacher(long id);
}
