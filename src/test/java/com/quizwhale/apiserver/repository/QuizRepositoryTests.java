package com.quizwhale.apiserver.repository;

import com.github.javafaker.Faker;
import com.quizwhale.apiserver.domain.Member;
import com.quizwhale.apiserver.domain.MemberRole;
import com.quizwhale.apiserver.domain.Quiz;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuizRepositoryTests {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(quizRepository, "QuizRepository should not be null");
        Assertions.assertNotNull(memberRepository, "MemberRepository should not be null");

        log.info(quizRepository.getClass().getName());
        log.info(memberRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsert() {

        Member member = memberRepository.save(Member.builder()
                .email(faker.internet().emailAddress())
                .password(passwordEncoder.encode(faker.internet().password()))
                .nickname(faker.name().name())
                .memberRole(MemberRole.USER)
                .build());

        Map<String, String> choices = Map.of(
                "A", faker.lorem().sentence(),
                "B", faker.lorem().sentence(),
                "C", faker.lorem().sentence(),
                "D", faker.lorem().sentence(),
                "E", faker.lorem().sentence()
        );

        Quiz result = quizRepository.save(Quiz.builder()
                        .title(faker.lorem().sentence())
                        .problem(faker.lorem().paragraph())
                        .choices(choices)
                        .answer(faker.lorem().word())
                        .isCorrect(faker.bool().bool())
                        .member(member)
                        .build());
        log.info(result);

    }

    @Test
    @Transactional
    public void testRead() {

        Long qno = 1L;
        Optional<Quiz> result = quizRepository.findById(qno);
        Quiz quiz = result.orElseThrow();

        Assertions.assertNotNull(quiz);
        log.info(quiz);

    }

    @Test
    public void testReadQuizByQno() {
        Long qno = 1L;
        Object result = quizRepository.getQuizByQno(qno);
        Object[] arr = (Object[]) result;
        log.info(Arrays.toString(arr));
    }

    @Test
    public void testDelete() {

        Long qno = 1L;
        quizRepository.deleteById(qno);
        Optional<Quiz> result = quizRepository.findById(qno);

        Assertions.assertEquals(result, Optional.empty());
        log.info(result);

    }

}
