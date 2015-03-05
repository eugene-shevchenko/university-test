package com.university.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by eshevchenko on 03.03.15.
 */
@Entity
public class Dialog extends AbstractPersistable<Long> {

    @OneToOne
    @JoinColumn(name = "questioner_id")
    private Person questioner;
    @OneToOne
    @JoinColumn(name = "answerer_id", nullable = true)
    private Person answerer;
    @OneToOne
    @JoinColumn(name = "question_id")
    private Phrase question;
    @OneToOne
    @JoinColumn(name = "answer_id")
    private Phrase answer;

    public Person getQuestioner() {
        return questioner;
    }

    public void setQuestioner(Person questioner) {
        this.questioner = questioner;
    }

    public Person getAnswerer() {
        return answerer;
    }

    public void setAnswerer(Person answerer) {
        this.answerer = answerer;
    }

    public Phrase getQuestion() {
        return question;
    }

    public void setQuestion(Phrase question) {
        this.question = question;
    }

    public Phrase getAnswer() {
        return answer;
    }

    public void setAnswer(Phrase answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        String questioner = getQuestioner() == null ? "Anonymous" : getQuestioner().toString();
        return String.format("%s:\n- %s\n%s:\n- %s", questioner, getQuestion(), getAnswerer(), getAnswer());
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        Dialog other = (Dialog) obj;
        return (getQuestion() == null ? (other.getQuestion() == null) : getQuestion().equals(other.getQuestion()))
                && (getQuestioner() == null ? (other.getQuestioner() == null) : getQuestioner().equals(other.getQuestioner()))
                && (getAnswer() == null ? (other.getAnswer() == null) : getAnswer().equals(other.getAnswer()))
                && (getAnswerer() == null ? (other.getAnswerer() == null) : getAnswerer().equals(other.getAnswerer()));
    }

    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
        hashCode += (getAnswer() == null ? 0 : getAnswer().hashCode()) * 31;
        hashCode += (getAnswerer() == null ? 0 : getAnswerer().hashCode()) * 31;
        hashCode += (getQuestion() == null ? 0 : getQuestion().hashCode()) * 31;
        hashCode += (getQuestioner() == null ? 0 : getQuestioner().hashCode()) * 31;
        return hashCode;
    }
}
