package com.example.sub.controller;

import com.example.sub.domain.entity.Member;
import com.example.sub.service.MemberService;
import com.example.sub.service.UsageAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UsageAnalyticsController {

    private final MemberService memberService;
    private final UsageAnalyticsService usageAnalyticsService;

    @GetMapping("/member/usage-summary")
    public String usageSummary(Principal principal, Model model) {
        Member member = memberService.findByEmail(principal.getName());
        model.addAttribute("member", member);
        model.addAttribute("usageStats", usageAnalyticsService.getUsageStats(member.getId()));
        return "member/usage-summary";
    }
}
