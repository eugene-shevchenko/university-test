package com.university.service;


import com.university.domain.Dialog;
import com.university.domain.Person;
import com.university.domain.Phrase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by eshevchenko on 04.03.15.
 */
@Service
public class TeacherServiceImpl extends BaseService implements TeacherService {

    @Autowired
    private DialogService dialogService;

    @Override
    public Person getNonFiredTeacher(Collection<Person> firedTeachers) {
        Collection<Long> exceptIds = new HashSet<Long>();
        for (Person firedTeacher : firedTeachers) {
            exceptIds.add(firedTeacher.getId());
        }
        Person teacher = firstFrom(personRepository.findRandomByRole(Person.Role.PHILOSOPHER, exceptIds, new PageRequest(0, 1)));
        getLogger().info(teacher + " was ordered.");
        return teacher;
    }

    @Override
    public Phrase answerTo(Phrase question, Person answerer) {
        Phrase answer = dialogService.getRandomAnswer();
        Dialog dialog = dialogService.saveAnonymousDialog(question, answer, answerer);
        getLogger().info(dialog);
        return answer;
    }

    @Override
    public void fireTeacher(Person teacher) {
        getLogger().info(teacher + " fired.");
    }

    @Override
    public List<Person> getAllTeachers() {
        return personRepository.findByRole(Person.Role.PHILOSOPHER);
    }

    @Override
    public Person getTeacher(long id) {
        return personRepository.findOne(id);
    }
}
