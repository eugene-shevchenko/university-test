package com.university.service;

import com.university.domain.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StudentServiceTest extends BaseServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void testStudentLife() {
        Person student = studentService.tryBornStudent(100);
        assertNotNull(student);
        assertFalse(student.isNew());
        assertTrue(student.isAlive());

        studentService.suicideStudent(student);
        student = studentService.getStudent(student.getId());
        assertNotNull(student);
        assertFalse(student.isAlive());
    }

    @Test
    public void test() {
        int attemptCount = 10;
        List<Person> students = tryBornStudents(attemptCount, 100);
        assertNotNull(students);
        assertEquals(attemptCount, students.size());

        students = studentService.getAliveStudents();
        assertNotNull(students);
        assertEquals(attemptCount, students.size());

        int bornChance = 30;
        students = tryBornStudents(100, bornChance);
        assertTrue(students.size() < bornChance + 10);
    }

    private List<Person> tryBornStudents(int attemptCount, int bornChance) {
        List<Person> students = new ArrayList<Person>();
        int i = 0;
        while (i++ < attemptCount) {
            Person student = studentService.tryBornStudent(bornChance);
            if (student != null) {
                students.add(student);
            }
        }
        return students;
    }
}