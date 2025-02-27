package com.quizwhale.apiserver.dto;

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

    private int startPage, endPage;

    private String keyword;

}
