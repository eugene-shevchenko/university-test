package com.university.service;


import com.university.domain.Dialog;
import com.university.domain.Person;
import com.university.domain.Phrase;

import java.util.List;

/**
 * Created by eshevchenko on 04.03.15.
 */
public interface DialogService {

    Phrase getRandomQuestion();

    Phrase getRandomAnswer();

    List<Dialog> getQuestionerDialogs(Person questioner);

    List<Dialog> getAnswererDialogs(Person answerer);

    Dialog saveDialog(Phrase question, Person questioner, Phrase answer, Person answerer);

    Dialog saveAnonymousDialog(Phrase question, Phrase answer, Person answerer);

    void startPhilosophicDialogsWith(Person questioner);

    int getActiveDialogsCount();
}
