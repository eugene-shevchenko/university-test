package com.university.teachers.controller;

import com.university.domain.Person;
import com.university.service.DialogService;
import com.university.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by eshevchenko on 02.03.15.
 */
@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService; 
    @Autowired
    private DialogService dialogService;

    @RequestMapping(value = {"/", "/teachers"})
    public ModelAndView getTeachers() {
        return new ModelAndView("teachers")
                .addObject("teachers", teacherService.getAllTeachers());
    }

    @RequestMapping(value = "/teachers/{teacherId}")
    public ModelAndView getTeacher(@PathVariable long teacherId) {
        Person teacher = teacherService.getTeacher(teacherId);
        return new ModelAndView("teacher")
                .addObject("teacher", teacher)
                .addObject("dialogs", dialogService.getAnswererDialogs(teacher));
    }
}
