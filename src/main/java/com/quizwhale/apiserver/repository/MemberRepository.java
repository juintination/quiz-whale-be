package com.quizwhale.apiserver.repository;

import com.quizwhale.apiserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Member findByEmail(String email);

    @Query("SELECT m, q " +
            " FROM Member m LEFT JOIN m.quizList q " +
            " WHERE m.mno = :mno" +
            " GROUP BY m, q")
    Object getMemberByMno(@Param("mno") Long mno);

}
