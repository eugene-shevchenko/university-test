package com.university.service;

import com.university.domain.Dialog;
import com.university.domain.Person;
import com.university.domain.Phrase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class DialogServiceTest extends BaseServiceTest {

    @Autowired
    private DialogService dialogService;
    @Autowired
    private PhilosopherService teacherService;
    @Autowired
    private StudentService studentService;

    @Value("${limit.oneQuestionAnswers:3}")
    private Integer oneQuestionAnswers;
    @Value("${limit.answerersPerQuestioner:3}")
    private Integer answerersPerQuestioner;

    @Test
    public void test() throws InterruptedException {
        Phrase question = dialogService.getRandomQuestion();
        Phrase answer = dialogService.getRandomAnswer();
        Person answerer = teacherService.getNonFiredTeacher(Collections.EMPTY_SET);
        Dialog dialog = dialogService.saveAnonymousDialog(question, answer, answerer);

        assertNotNull(dialog);
        assertFalse(dialog.isNew());

        Person student = studentService.tryBornStudent(100);
        dialogService.startPhilosophicDialogsWith(student);
        while (dialogService.getActiveDialogsCount() > 0) {
            Thread.sleep(500);
        }
        
        List<Dialog> studentDialogs = dialogService.getQuestionerDialogs(student);
        assertNotNull(studentDialogs);
        assertEquals(oneQuestionAnswers * answerersPerQuestioner, studentDialogs.size());
    }
}