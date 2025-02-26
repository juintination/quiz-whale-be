package com.quizwhale.apiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.quizwhale.apiserver.domain.Member;
import com.quizwhale.apiserver.dto.MemberDTO;
import com.quizwhale.apiserver.repository.MemberRepository;
import com.quizwhale.apiserver.util.CustomServiceException;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO get(Long mno) {
        Optional<Member> result = memberRepository.findById(mno);
        Member member = result.orElseThrow(() -> new CustomServiceException("NOT_EXIST_MEMBER"));
        return entityToDTO(member);
    }

    @Override
    public Long getMno(String email) {
        Optional<Member> result = Optional.ofNullable(memberRepository.findByEmail(email));
        Member member = result.orElseThrow(() -> new CustomServiceException("NO_EMAIL_EXISTS"));
        return member.getMno();
    }

    @Override
    public Long register(MemberDTO memberDTO) throws CustomServiceException {

        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new CustomServiceException("EMAIL_ALREADY_EXISTS");
        }

        if (memberRepository.existsByNickname(memberDTO.getNickname())) {
            throw new CustomServiceException("NICKNAME_ALREADY_EXISTS");
        }

        Member member = memberRepository.save(dtoToEntity(memberDTO));
        return member.getMno();
    }

    @Override
    public void modify(MemberDTO memberDTO) throws CustomServiceException {
        Optional<Member> result = memberRepository.findById(memberDTO.getMno());
        Member member = result.orElseThrow();

        log.info("memberDTO: " + memberDTO);

        if (memberDTO.getEmail() != null && !memberDTO.getEmail().isEmpty()) {
            if (!member.getEmail().equals(memberDTO.getEmail()) && memberRepository.existsByEmail(memberDTO.getEmail())) {
                throw new CustomServiceException("EMAIL_ALREADY_EXISTS");
            }
            try {
                member.changeEmail(memberDTO.getEmail());
            } catch (IllegalArgumentException e) {
                throw new CustomServiceException("INVALID_EMAIL");
            }
        }

        if (memberDTO.getNickname() != null && !memberDTO.getNickname().isEmpty()) {
            if (!member.getNickname().equals(memberDTO.getNickname()) && memberRepository.existsByNickname(memberDTO.getNickname())) {
                throw new CustomServiceException("NICKNAME_ALREADY_EXISTS");
            }
            member.changeNickname(memberDTO.getNickname());
        }

        if (memberDTO.getPassword() != null && !memberDTO.getPassword().isEmpty()) {
            member.changePassword(passwordEncoder.encode(memberDTO.getPassword()));
        }

        if (memberDTO.getNickname() != null && !memberDTO.getNickname().isEmpty()) {
            member.changeNickname(memberDTO.getNickname());
        }

        memberRepository.save(member);
    }

    @Override
    public void remove(Long mno) {

        if (!memberRepository.existsById(mno)) {
            throw new CustomServiceException("NOT_EXIST_MEMBER");
        }

        memberRepository.deleteById(mno);
    }

    @Override
    public void checkPassword(Long mno, String password) {
        Member member = memberRepository.findById(mno).orElseThrow(() -> new CustomServiceException("NOT_EXIST_MEMBER"));
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomServiceException("INVALID_PASSWORD");
        }
    }

    @Override
    public Member dtoToEntity(MemberDTO memberDTO) {
        return Member.builder()
                .mno(memberDTO.getMno())
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword() != null ? passwordEncoder.encode(memberDTO.getPassword()) : null)
                .nickname(memberDTO.getNickname())
                .memberRole(memberDTO.getRole())
                .build();
    }

}
