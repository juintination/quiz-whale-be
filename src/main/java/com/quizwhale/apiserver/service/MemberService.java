package com.quizwhale.apiserver.service;

import com.quizwhale.apiserver.domain.Member;
import com.quizwhale.apiserver.dto.MemberDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {

    MemberDTO get(Long mno);

    Long getMno(String email);

    Long register(MemberDTO memberDTO);

    void modify(MemberDTO modifyDTO);

    void remove(Long mno);

    void checkPassword(Long mno, String password);

    Member dtoToEntity(MemberDTO memberDTO);

    default MemberDTO entityToDTO(Member member) {
        return MemberDTO.builder()
                .mno(member.getMno())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .role(member.getMemberRole())
                .regDate(member.getRegDate())
                .modDate(member.getModDate())
                .build();
    }

}
