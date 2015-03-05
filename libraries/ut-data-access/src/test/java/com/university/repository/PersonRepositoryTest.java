package com.university.repository;

import com.university.domain.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by eshevchenko on 03.03.15.
 */
public class PersonRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void test() {
        Person person = personRepository.saveAndFlush(Person.newPhilosopher("Immanuel Kant"));
        assertNotNull(person);
        assertFalse(person.isNew());
        assertEquals(Person.Role.PHILOSOPHER, person.getRole());

        person = personRepository.saveAndFlush(Person.newStudent("Smart Student"));
        assertEquals(Person.Role.STUDENT, person.getRole());

        List<Person> exceptTeachers = personRepository.findRandomByRole(Person.Role.PHILOSOPHER, Collections.EMPTY_SET, new PageRequest(0, 5));
        assertNotNull(exceptTeachers);
        assertEquals(5, exceptTeachers.size());

        Set<Long> exceptIds = new HashSet<>();
        for (Person teacher : exceptTeachers) {
            exceptIds.add(teacher.getId());
        }

        int i = 0;
        while (i++ < 10) {
            List<Person> randomTeacher = personRepository.findRandomByRole(Person.Role.PHILOSOPHER, exceptIds, new PageRequest(0, 3));
            for (Person teacher : randomTeacher) {
                assertFalse(exceptIds.contains(teacher.getId()));
            }
        }
    }
}
