package com.example.sub.controller;

import com.example.sub.repository.CancellationLogRepository;
import com.example.sub.repository.MemberRepository;
import com.example.sub.service.CancellationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CancellationController {

    private final CancellationService cancellationService;
    private final CancellationLogRepository logRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/cancellation/request/{subscriptionId}")
    public String requestForm(@PathVariable Long subscriptionId, Model model) {
        model.addAttribute("subscriptionId", subscriptionId);
        return "subscribe/cancel-request";
    }

// 임시: Security 없을 때
    @PostMapping("/cancellation/cancel")
    public String cancel(Principal principal,
                         @RequestParam Long subscriptionId,
                         @RequestParam String reason) {
        var member = memberRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        cancellationService.cancelSubscription(member.getId(), subscriptionId, reason);
        return "redirect:/subscriptions";
    }

    @GetMapping("/cancellation/recover")
    public String recover(@RequestParam String token, RedirectAttributes ra) {
        try {
            cancellationService.recoverByToken(token);
            ra.addFlashAttribute("message", "구독이 복구되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/subscriptions";
    }

    @GetMapping({"/cancellation/my-history", "/cancellation-log"})
    public String myHistory(Principal principal, Model model) {
        var member = memberRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        var logs = logRepository.findByMemberIdOrderByCancelledAtDesc(member.getId());
        model.addAttribute("logs", logs);
        return "cancellation-log";
    }
}