package com.university.repository;

import com.university.domain.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by eshevchenko on 03.03.15.
 */
public interface DialogRepository extends JpaRepository<Dialog, Long> {

    @Query("select d from Dialog d where answerer_id = ?1")
    List<Dialog> findAnswererDialogs(Long answererId);

    @Query("select d from Dialog d where questioner_id = ?1")
    List<Dialog> findQuestionerDialogs(Long questionerId);
}
