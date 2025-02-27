package com.quizwhale.apiserver.dto;

import com.quizwhale.apiserver.domain.QuizType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequestDTO {

    private MultipartFile file;

    private Long mno;

    private QuizType type;

    private int start, end;

    private String keyword;

}
