package com.university.service;

import com.university.domain.Person;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by eshevchenko on 04.03.15.
 */
public interface StudentService {
    
    @Nullable
    Person tryBornStudent(int bornChance);

    void suicideStudent(Person student);

    List<Person> getAliveStudents();

    List<Person> getDeadStudents();

    Person getStudent(long id);
}
