package com.quizwhale.apiserver.service;

import com.github.javafaker.Faker;
import com.quizwhale.apiserver.domain.MemberRole;
import com.quizwhale.apiserver.dto.MemberDTO;
import com.quizwhale.apiserver.dto.QuizDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuizServiceTests {

    @Autowired
    private QuizService quizService;

    @Autowired
    private MemberService memberService;

    private final Faker faker = new Faker();

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(quizService, "QuizService should not be null");
        Assertions.assertNotNull(memberService, "MemberService should not be null");

        log.info(quizService.getClass().getName());
        log.info(memberService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {

        MemberDTO memberDTO = MemberDTO.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .nickname(faker.name().name())
                .role(MemberRole.USER)
                .build();
        log.info(memberDTO);

        Long mno = memberService.register(memberDTO);
        log.info(mno);
        log.info(memberService.get(mno));

        QuizDTO quizDTO = QuizDTO.builder()
                .mno(mno)
                .title(faker.lorem().sentence())
                .content(faker.lorem().paragraph())
                .answer(faker.lorem().word())
                .isCorrect(false)
                .build();
        Long qno = quizService.register(quizDTO);
        log.info(qno);

    }

    @Test
    public void testGet() {
        Long qno = 1L;
        QuizDTO quizDTO = quizService.get(qno);
        Assertions.assertNotNull(quizDTO);
        log.info(quizDTO);
        log.info(quizService.dtoToEntity(quizDTO));
    }

    @Test
    public void testMakeAsCorrect() {
        Long qno = 1L;
        quizService.makeAsCorrect(qno);

        QuizDTO quizDTO = quizService.get(qno);
        Assertions.assertTrue(quizDTO.isCorrect());
        log.info(quizDTO);
    }

}
