package com.quizwhale.apiserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "member")
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qno;

    private int id;

    private String title;

    private String problem;

    @ElementCollection
    @CollectionTable(name = "quiz_choices", joinColumns = @JoinColumn(name = "quiz_id"))
    @MapKeyColumn(name = "choice_key")
    @Column(name = "choice_value")
    private Map<String, String> choices;

    private String answer;

    private String explanation;

    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public boolean isCorrect() {
        return isCorrect;
    }

    public void makeAsCorrect() {
        this.isCorrect = true;
    }

}
