package com.university.students.controller;

import com.university.domain.Dialog;
import com.university.domain.Person;
import com.university.service.DialogService;
import com.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.university.service.DialogServiceImpl.AnswersCounter;

/**
 * Created by eshevchenko on 02.03.15.
 */
@Controller
@EnableScheduling
public class StudentController {

    @Value("${bornChance:30}")
    private Integer bornChance;
    @Autowired
    private StudentService studentService;
    @Autowired
    private DialogService dialogService;

    @Scheduled(fixedDelay = 1000)
    public void checkNewStudents() {
        Person student = studentService.tryBornStudent(bornChance);
        if (student != null) {
            dialogService.startPhilosophicDialogsWith(student);
        }
    }

    @RequestMapping(value = {"/", "students"})
    public ModelAndView getStudents() {
        return new ModelAndView("students")
                .addObject("aliveStudents", studentService.getAliveStudents())
                .addObject("deadStudents", studentService.getDeadStudents());
    }

    @RequestMapping(value = "/students/{studentId}")
    public ModelAndView getStudent(@PathVariable long studentId) {
        Person student = studentService.getStudent(studentId);
        List<Dialog> dialogs = dialogService.getQuestionerDialogs(student);
        return new ModelAndView("student")
                .addObject("student", student)
                .addObject("answersCounter", AnswersCounter.fromDialogs(student, dialogs));
    }
}
