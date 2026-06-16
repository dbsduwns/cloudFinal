package com.example.sub.service;

import com.example.sub.domain.entity.Member;
import com.example.sub.domain.entity.Role;
import com.example.sub.dto.MemberForm;
import com.example.sub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(MemberForm form) {
        Member member = Member.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .name(form.getName())
                .phone(form.getPhone())
                .role(Role.USER)
                .build();
        return memberRepository.save(member).getId();
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }

    @Transactional
    public void update(String email, MemberForm form) {
        Member member = findByEmail(email);
        member.setName(form.getName());
        member.setPhone(form.getPhone());
        if (form.getPassword() != null && !form.getPassword().isEmpty()) {
            member.setPassword(passwordEncoder.encode(form.getPassword()));
        }
    }
}
