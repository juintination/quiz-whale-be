package com.quizwhale.apiserver.security.service;

import com.quizwhale.apiserver.domain.Member;
import com.quizwhale.apiserver.repository.MemberRepository;
import com.quizwhale.apiserver.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("----------------------loadUserByUsername----------------------");

        Member member = memberRepository.findByEmail(username);

        if (member == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        log.info("loadUserByUsername: " + member);
        return new CustomUserDetails(member);

    }

}
