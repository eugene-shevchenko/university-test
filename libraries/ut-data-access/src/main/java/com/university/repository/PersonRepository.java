package com.university.repository;

import com.university.domain.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * Created by eshevchenko on 03.03.15.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.role = ?1 and p.id not in ?2 order by random()")
    List<Person> findRandomByRole(Person.Role role, Collection<Long> exceptIds, Pageable pageable);

    List<Person> findByRole(Person.Role role);

    List<Person> findByRoleAndDeathDateIsNull(Person.Role role);
    
    List<Person> findByRoleAndDeathDateIsNotNull(Person.Role role);
}
