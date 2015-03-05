package com.university.repository;

import com.university.domain.Dialog;
import com.university.domain.Person;
import com.university.domain.Phrase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class DialogRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PhraseRepository phraseRepository;
    @Autowired
    private DialogRepository dialogRepository;

    @Test
    public void test() {
        Person student = personRepository.saveAndFlush(Person.newStudent("William Shakespeare"));
        Phrase question = phraseRepository.saveAndFlush(Phrase.newQuestion("2b || !2b ?"));

        Person philosopher = personRepository.saveAndFlush(Person.newPhilosopher("Karl Marks"));
        Phrase answer = phraseRepository.saveAndFlush(Phrase.newAnswer("Of course 2b!"));

        Dialog dialog = new Dialog();
        dialog.setQuestioner(student);
        dialog.setQuestion(question);
        dialog.setAnswerer(philosopher);
        dialog.setAnswer(answer);

        dialog = dialogRepository.saveAndFlush(dialog);
        assertNotNull(dialog);
        assertFalse(dialog.isNew());

        List<Dialog> dialogs = dialogRepository.findAnswererDialogs(philosopher.getId());
        assertNotNull(dialogs);
        assertFalse(dialogs.isEmpty());
        assertEquals(1, dialogs.size());

        dialogs = dialogRepository.findQuestionerDialogs(student.getId());
        assertNotNull(dialogs);
        assertFalse(dialogs.isEmpty());
        assertEquals(1, dialogs.size());
    }
}