package com.example.sub.controller;

import com.example.sub.domain.entity.Member;
import com.example.sub.dto.MemberForm;
import com.example.sub.repository.MemberSubscriptionRepository;
import com.example.sub.service.MemberService;
import com.example.sub.service.UsageAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberSubscriptionRepository memberSubscriptionRepository;
    private final UsageAnalyticsService usageAnalyticsService;

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(MemberForm form) {
        memberService.join(form);
        return "redirect:/login";
    }

    @GetMapping("/member/mypage")
    public String mypage(Principal principal, Model model) {
        Member member = memberService.findByEmail(principal.getName());
        model.addAttribute("member", member);
        model.addAttribute("subscriptions", memberSubscriptionRepository.findByMemberId(member.getId()));
        model.addAttribute("usageStats", usageAnalyticsService.getUsageStats(member.getId()));
        return "member/mypage";
    }

    @GetMapping("/member/update")
    public String updateForm(Principal principal, Model model) {
        Member member = memberService.findByEmail(principal.getName());
        MemberForm form = new MemberForm();
        form.setEmail(member.getEmail());
        form.setName(member.getName());
        form.setPhone(member.getPhone());
        model.addAttribute("memberForm", form);
        return "member/update";
    }

    @PostMapping("/member/update")
    public String update(Principal principal, MemberForm form) {
        memberService.update(principal.getName(), form);
        return "redirect:/member/mypage";
    }
}
