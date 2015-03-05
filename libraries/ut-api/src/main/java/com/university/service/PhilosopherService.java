package com.university.service;

import com.university.domain.Person;
import com.university.domain.Phrase;

import java.util.Collection;

/**
 * Created by eshevchenko on 05.03.15.
 */
public interface PhilosopherService {
    Person getNonFiredTeacher(Collection<Person> firedTeachers);

    Phrase answerTo(Phrase question, Person answerer);

    void fireTeacher(Person teacher);
}
