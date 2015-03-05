package com.university.repository;

import com.university.domain.Phrase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by eshevchenko on 03.03.15.
 */
public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    @Query("select p from Phrase p where p.type = ?1 order by random()")
    List<Phrase> findRandomByType(Phrase.Type type, Pageable pageable);
}
