package com.quizwhale.apiserver.service;

import com.quizwhale.apiserver.domain.Member;
import com.quizwhale.apiserver.domain.Quiz;
import com.quizwhale.apiserver.dto.QuizDTO;
import com.quizwhale.apiserver.repository.QuizRepository;
import com.quizwhale.apiserver.util.CustomServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    @Override
    public QuizDTO get(Long qno) {
        Object result = quizRepository.getQuizByQno(qno);
        if (result == null) {
            throw new CustomServiceException("NOT_EXIST_QUIZ");
        }
        Object[] arr = (Object[]) result;
        return entityToDTO((Quiz) arr[0], (Member) arr[1]);
    }

    @Override
    public Long register(QuizDTO quizDTO) {
        Quiz quiz = dtoToEntity(quizDTO);
        Quiz result = quizRepository.save(quiz);
        return result.getQno();
    }

    @Override
    public void remove(Long qno) {
        if (!quizRepository.existsById(qno)) {
            throw new CustomServiceException("NOT_EXIST_QUIZ");
        }
        quizRepository.deleteById(qno);
    }

    @Override
    public void makeAsCorrect(Long qno) {
        Optional<Quiz> result = quizRepository.findById(qno);
        Quiz quiz = result.orElseThrow(() -> new CustomServiceException("NOT_EXIST_QUIZ"));
        quiz.makeAsCorrect();
        quizRepository.save(quiz);
    }

    @Override
    public Quiz dtoToEntity(QuizDTO quizDTO) {
        Member member = Member.builder().mno(quizDTO.getMno()).build();
        return Quiz.builder()
                .qno(quizDTO.getQno())
                .title(quizDTO.getTitle())
                .content(quizDTO.getContent())
                .answer(quizDTO.getAnswer())
                .isCorrect(quizDTO.isCorrect())
                .member(member)
                .build();
    }

}
