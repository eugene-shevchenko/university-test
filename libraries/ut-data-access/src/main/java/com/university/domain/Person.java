package com.university.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by eshevchenko on 03.03.15.
 */
@Entity
public class Person extends AbstractPersistable<Long> {

    public enum Role {
        PHILOSOPHER, STUDENT;
    }

    private Role role;
    private String name;
    private Date birthDate = new Date();
    @Column(nullable = true)
    private Date deathDate;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public boolean isAlive() {
        return getDeathDate() == null;
    }
    
    private static Person newPerson(Role role, String name) {
        Person result = new Person();
        result.setRole(role);
        result.setName(name);

        return result;
    }

    public static Person newPhilosopher(String name) {
        return newPerson(Role.PHILOSOPHER, name);
    }

    public static Person newStudent(String name) {
        return newPerson(Role.STUDENT, name);
    }

    @Override
    public String toString() {
        return isNew()
                ? String.format("%s: %s", getRole(), getName())
                : String.format("%s #%d: %s", getRole(), getId(), getName());
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            Person other = (Person) obj;
            return (getRole() == other.getRole()) && getName().equals(other.getName());
        }
        return equals;
    }

    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
        hashCode += (getRole() == null ? 0 : getRole().hashCode()) * 31;
        hashCode += (getName() == null ? 0 : getName().hashCode()) * 31;
        return hashCode;
    }
}
