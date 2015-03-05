package com.university.repository;

import com.university.domain.Phrase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.junit.Assert.*;

public class PhraseRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private PhraseRepository phraseRepository;

    @Test
    public void test() {
        Phrase answer = phraseRepository.saveAndFlush(Phrase.newAnswer("answer"));
        assertNotNull(answer);
        assertFalse(answer.isNew());
        assertEquals(Phrase.Type.ANSWER, answer.getType());

        for (Phrase phrase : phraseRepository.findAll()) {
            System.out.println(phrase.getContent());
        }



        int i = 0;
        while (i++ < 10) {

            List<Phrase> phrases = phraseRepository.findRandomByType(Phrase.Type.QUESTION, new PageRequest(0, 1));
            System.out.println(StringUtils.collectionToDelimitedString(phrases, " "));
            phrases = phraseRepository.findRandomByType(Phrase.Type.ANSWER, new PageRequest(0, 1));
            System.out.println(StringUtils.collectionToCommaDelimitedString(phrases));
        }

    }
}