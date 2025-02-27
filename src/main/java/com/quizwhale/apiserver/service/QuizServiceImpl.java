package com.quizwhale.apiserver.service;

import com.quizwhale.apiserver.domain.Member;
import com.quizwhale.apiserver.domain.Quiz;
import com.quizwhale.apiserver.dto.QuizDTO;
import com.quizwhale.apiserver.dto.QuizRequestDTO;
import com.quizwhale.apiserver.dto.QuizResponseDTO;
import com.quizwhale.apiserver.repository.QuizRepository;
import com.quizwhale.apiserver.util.CustomServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    @Value("${ai.server.url}")
    private String aiServerUrl;

    private final QuizRepository quizRepository;

    @Override
    public QuizDTO get(Long qno) {
        Object result = quizRepository.getQuizByQno(qno);
        if (result == null) {
            throw new CustomServiceException("NOT_EXIST_QUIZ");
        }
        Object[] arr = (Object[]) result;
        Quiz quiz = (Quiz) arr[0];
        Member member = (Member) arr[1];
        quiz.getChoices().size();
        return entityToDTO(quiz, member);
    }

    @Override
    public QuizResponseDTO uploadAndGetList(QuizRequestDTO quizRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", quizRequestDTO.getFile().getResource());
        body.add("type", quizRequestDTO.getFile().getContentType());
        body.add("start", quizRequestDTO.getStartPage());
        body.add("end", quizRequestDTO.getEndPage());
        body.add("keyword", quizRequestDTO.getKeyword());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<List<QuizDTO>> response = new RestTemplate().exchange(
                aiServerUrl,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomServiceException("AI_SERVER_REQUEST_FAILED");
        }

        log.info("response: " + response.getBody());
        List<QuizDTO> quizList = response.getBody();
        for (QuizDTO quizDTO : quizList) {
            quizDTO.setQno(register(quizDTO));
        }

        return QuizResponseDTO.builder().quizzes(quizList).build();
    }

    @Override
    public Long register(QuizDTO quizDTO) {
        Quiz result = quizRepository.save(dtoToEntity(quizDTO));
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
                .id(quizDTO.getId())
                .title(quizDTO.getTitle())
                .problem(quizDTO.getProblem())
                .choices(quizDTO.getChoices())
                .answer(quizDTO.getAnswer())
                .explanation(quizDTO.getExplanation())
                .isCorrect(quizDTO.isCorrect())
                .member(member)
                .build();
    }

}
