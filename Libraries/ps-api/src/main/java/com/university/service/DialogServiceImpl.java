package com.university.service;

import com.university.domain.Dialog;
import com.university.domain.Person;
import com.university.domain.Phrase;
import com.university.repository.DialogRepository;
import com.university.repository.PhraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by eshevchenko on 04.03.15.
 */
@Service
@EnableScheduling
public class DialogServiceImpl extends BaseService implements DialogService {

    @Value("${limit.oneQuestionAnswers:3}")
    private Integer oneQuestionAnswersLimit;
    @Value("${limit.answerersPerQuestioner:3}")
    private Integer answerersPerQuestionerLimit;
    @Autowired
    private PhraseRepository phraseRepository;
    @Autowired
    private DialogRepository dialogRepository;
    @Autowired
    private PhilosopherService teacherService;
    @Autowired
    private StudentService studentService;

    private final ConcurrentMap<Person, Thread> dialogs = new ConcurrentHashMap<>();
    
    @Scheduled(fixedDelay = 1000)
    public void checkFinishedDialogs() {
        Set<Person> questioners = dialogs.keySet();
        Iterator<Person> iterator = questioners.iterator();
        while (iterator.hasNext()) {
            Person next = iterator.next();
            if (!dialogs.get(next).isAlive()) {
                dialogs.remove(next);
            }
        }
    }
    
    private Phrase getRandomPhrase(Phrase.Type type) {
        return firstFrom(phraseRepository.findRandomByType(type, new PageRequest(0, 1)));
    }

    @Override
    public Phrase getRandomQuestion() {
        return getRandomPhrase(Phrase.Type.QUESTION);
    }

    @Override
    public Phrase getRandomAnswer() {
        return getRandomPhrase(Phrase.Type.ANSWER);
    }

    @Override
    public List<Dialog> getQuestionerDialogs(Person questioner) {
        return dialogRepository.findQuestionerDialogs(questioner.getId());
    }

    @Override
    public List<Dialog> getAnswererDialogs(Person answerer) {
        return dialogRepository.findAnswererDialogs(answerer.getId());
    }

    @Override
    public Dialog saveDialog(Phrase question, Person questioner, Phrase answer, Person answerer) {
        Dialog dialog = new Dialog();
        dialog.setQuestion(question);
        dialog.setQuestioner(questioner);
        dialog.setAnswer(answer);
        dialog.setAnswerer(answerer);

        return dialogRepository.saveAndFlush(dialog);
    }
    
    @Override
    public Dialog saveAnonymousDialog(Phrase question, Phrase answer, Person answerer) {
        return saveDialog(question, null, answer, answerer);
    }

    @Override
    public void startPhilosophicDialogsWith(Person questioner) {
        PhilosophicDialogs dialogs = new PhilosophicDialogs(questioner);
        this.dialogs.put(questioner, dialogs);
        dialogs.start();
    }

    @Override
    public int getActiveDialogsCount() {
        return dialogs.size();
    }

    public static class AnswersCounter extends HashMap<Person, Map<Phrase, Set<Phrase>>> {
        
        private Person questioner;

        public AnswersCounter(Person questioner) {
            this.questioner = questioner;
        }

        public int addAnswerToQuestion(Phrase question, Phrase answer, Person answerer) {
            Map<Phrase, Set<Phrase>> answersByQuestion = get(answerer);
            if (answersByQuestion == null) {
                answersByQuestion = new HashMap<Phrase, Set<Phrase>>();
                put(answerer, answersByQuestion);
            }
            Set<Phrase> answers = answersByQuestion.get(question);
            if (answers == null) {
                answers = new HashSet<Phrase>();
                answersByQuestion.put(question, answers);
            }
            answers.add(answer);

            return answers.size();
        }
        
        public List<Dialog> answersAsDialogs(Phrase question, Person answerer) {
            List<Dialog> result = new ArrayList<Dialog>();

            for (Phrase answer : get(answerer).get(question)) {
                Dialog dialog = new Dialog();
                dialog.setQuestion(question);
                dialog.setQuestioner(questioner);
                dialog.setAnswer(answer);
                dialog.setAnswerer(answerer);

                result.add(dialog);
            }

            return result;
        }
        
        public static AnswersCounter fromDialogs(Person questioner, List<Dialog> dialogs) {
            AnswersCounter counter = new AnswersCounter(questioner);
            for (Dialog dialog : dialogs) {
                counter.addAnswerToQuestion(dialog.getQuestion(), dialog.getAnswer(), dialog.getAnswerer());
            }
            return counter;    
        }
    }
    
    private class PhilosophicDialogs extends Thread {

        private final Person student;
        private Person teacher;
        private Set<Person> firedTeachers = new HashSet<Person>();
        private AnswersCounter answersCounter;

        public PhilosophicDialogs(Person student) {
            super(student + " philosophic dialogs");
            this.student = student;
            this.answersCounter = new AnswersCounter(student);
        }

        @Override
        public void run() {
            getLogger().info(getName() + " started ...");
            teacher = teacherService.getNonFiredTeacher(Collections.EMPTY_SET);
            while (true) {
                Phrase question = getRandomQuestion();
                Phrase answer = teacherService.answerTo(question, teacher);

                if (answersCounter.addAnswerToQuestion(question, answer, teacher) == oneQuestionAnswersLimit) {
                    teacherService.fireTeacher(teacher);
                    firedTeachers.add(teacher);
                    for (Dialog dialog : answersCounter.answersAsDialogs(question, teacher)) {
                        dialogRepository.save(dialog);
                    }

                    if (firedTeachers.size() == answerersPerQuestionerLimit) {
                        getLogger().info(getName() + " finished.");
                        studentService.suicideStudent(student);
                        return;
                    }

                    this.teacher = teacherService.getNonFiredTeacher(firedTeachers);
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    getLogger().error(getName() + " error: ", e);
                }
            }
        }

    }
}
