package com.quizwhale.apiserver.controller;

import com.quizwhale.apiserver.dto.QuizDTO;
import com.quizwhale.apiserver.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{qno}")
    public QuizDTO get(@PathVariable("qno") Long qno) {
        return quizService.get(qno);
    }

    @PostMapping("/")
    @PreAuthorize("#quizDTO.mno == authentication.principal.mno")
    public Map<String, Long> register(QuizDTO quizDTO) {
        long qno = quizService.register(quizDTO);
        return Map.of("QNO", qno);
    }

    @PatchMapping("/correct/{qno}")
    public Map<String, String> makeAsCorrect(@PathVariable(name="qno") Long qno) {
        quizService.makeAsCorrect(qno);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{qno}")
    public Map<String, String> remove(@PathVariable(name="qno") Long qno) {
        quizService.remove(qno);
        return Map.of("RESULT", "SUCCESS");
    }

}
