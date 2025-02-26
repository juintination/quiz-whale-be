package com.quizwhale.apiserver.repository;

import com.quizwhale.apiserver.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q, m FROM Quiz q LEFT JOIN q.member m WHERE q.qno = :qno")
    Object getQuizByQno(@Param("qno") Long qno);

}
