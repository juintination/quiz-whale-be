package com.quizwhale.apiserver.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuizResponseDTO {

    private List<QuizDTO> quizzes;

}
