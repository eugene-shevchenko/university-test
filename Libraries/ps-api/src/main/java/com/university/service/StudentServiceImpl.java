package com.university.service;

import com.university.domain.Person;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by eshevchenko on 04.03.15.
 */
@Service
public class StudentServiceImpl extends BaseService implements StudentService {

    private static final Random RANDOM = new Random();

    @Resource(name = "maleNames")
    private List<String> maleNames;
    @Resource(name = "femaleNames")
    private List<String> femaleNames;

    @Nullable
    @Override
    public Person tryBornStudent(int bornChance) {
        Person result = null;
        int chance = RANDOM.nextInt(100);
        if (chance < bornChance) {
            result = personRepository.saveAndFlush(createStudent(chance < bornChance % 2));
            getLogger().info(result + " was born.");
        }
        return result;
    }

    @Override
    public void suicideStudent(Person student) {
        student.setDeathDate(new Date());
        personRepository.save(student);
        getLogger().info(student + " commit suicide :(");
    }
    
    private Person createStudent(boolean isMale) {
        List<String> firstNames = isMale ? maleNames : femaleNames;
        return Person.newStudent(randomName(firstNames) + " " + randomName(maleNames));
    }

    @Override
    public List<Person> getAliveStudents() {
        return personRepository.findByRoleAndDeathDateIsNull(Person.Role.STUDENT);
    }

    @Override
    public List<Person> getDeadStudents() {
        return personRepository.findByRoleAndDeathDateIsNotNull(Person.Role.STUDENT);
    }

    @Override
    public Person getStudent(long id) {
        return personRepository.findOne(id);
    }

    private static String randomName(List<String> names) {
        return names.get(RANDOM.nextInt(names.size()));
    }
}
