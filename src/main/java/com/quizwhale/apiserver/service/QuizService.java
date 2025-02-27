package com.quizwhale.apiserver.service;

import com.quizwhale.apiserver.domain.Member;
import com.quizwhale.apiserver.domain.Quiz;
import com.quizwhale.apiserver.dto.QuizDTO;
import com.quizwhale.apiserver.dto.QuizRequestDTO;
import com.quizwhale.apiserver.dto.QuizResponseDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface QuizService {

    QuizDTO get(Long qno);

    QuizResponseDTO uploadAndGetList(QuizRequestDTO quizRequestDTO);

    Long register(QuizDTO quizDTO);

    void remove(Long qno);

    void makeAsCorrect(Long qno);

    Quiz dtoToEntity(QuizDTO quizDTO);

    default QuizDTO entityToDTO(Quiz quiz, Member member) {
        return QuizDTO.builder()
                .qno(quiz.getQno())
                .mno(member.getMno())
                .id(quiz.getId())
                .title(quiz.getTitle())
                .problem(quiz.getProblem())
                .choices(quiz.getChoices())
                .answer(quiz.getAnswer())
                .explanation(quiz.getExplanation())
                .isCorrect(quiz.isCorrect())
                .regDate(quiz.getRegDate())
                .modDate(quiz.getModDate())
                .build();
    }

}
