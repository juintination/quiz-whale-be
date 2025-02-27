package com.quizwhale.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO {

    private Long qno, mno;

    private int id;

    private String title, problem, answer, explanation;

    private Map<String, String> choices;

    private boolean isCorrect;

    private LocalDateTime regDate, modDate;

}
