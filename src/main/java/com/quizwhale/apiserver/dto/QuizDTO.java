package com.quizwhale.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO {

    private Long qno, mno;

    private String title, content, answer;

    private boolean isCorrect;

    private LocalDateTime regDate, modDate;

}
