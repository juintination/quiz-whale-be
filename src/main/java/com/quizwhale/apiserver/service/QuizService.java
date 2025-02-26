package com.quizwhale.apiserver.service;

import com.quizwhale.apiserver.domain.Member;
import com.quizwhale.apiserver.domain.Quiz;
import com.quizwhale.apiserver.dto.QuizDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface QuizService {

    QuizDTO get(Long qno);

    Long register(QuizDTO quizDTO);

    void remove(Long qno);

    void makeAsCorrect(Long qno);

    Quiz dtoToEntity(QuizDTO quizDTO);

    default QuizDTO entityToDTO(Quiz quiz, Member member) {
        return QuizDTO.builder()
                .qno(quiz.getQno())
                .mno(member.getMno())
                .title(quiz.getTitle())
                .content(quiz.getContent())
                .answer(quiz.getAnswer())
                .isCorrect(quiz.isCorrect())
                .regDate(quiz.getRegDate())
                .modDate(quiz.getModDate())
                .build();
    }

}
