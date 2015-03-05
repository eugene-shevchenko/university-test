package com.university.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

/**
 * Created by eshevchenko on 02.03.15.
 */
@Entity
public class Phrase extends AbstractPersistable<Long> {

    public enum Type {
        QUESTION, ANSWER;
    }

    private Type type;
    private String content;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private static Phrase newPhrase(Type type, String text) {
        Phrase result = new Phrase();
        result.setType(type);
        result.setContent(text);
        return result;
    }

    public static Phrase newQuestion(String text) {
        return newPhrase(Type.QUESTION, text);
    }

    public static Phrase newAnswer(String text) {
        return newPhrase(Type.ANSWER, text);
    }

    @Override
    public String toString() {
        return getContent();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            Phrase other = (Phrase) obj;
            return (getType() == other.getType()) && getContent().equals(other.getContent());
        }

        return equals;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += (getType() == null ? 0 : getType().hashCode()) * 31;
        result += (getContent() == null ? 0 : getContent().hashCode()) * 31;

        return result;
    }
}
