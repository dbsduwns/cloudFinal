package com.example.sub.controller;

import com.example.sub.service.MemberService;
import com.example.sub.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlanController {

    private final SubscriptionService subscriptionService;
    private final MemberService memberService;

    @GetMapping("/")
    public String dashboard(Principal principal, Model model) {
        if (principal != null) {
            com.example.sub.domain.entity.Member member = memberService.findByEmail(principal.getName());
            model.addAttribute("subscriptions", subscriptionService.findMemberSubscriptions(member.getId()));
        } else {
            model.addAttribute("subscriptions", List.of());
        }
        return "index";
    }

    @GetMapping("/plans")
    public String list(java.security.Principal principal, Model model) {
        // Model 규약: plans
        model.addAttribute("plans", subscriptionService.findAllPlans());
        if (principal != null) {
            com.example.sub.domain.entity.Member member = memberService.findByEmail(principal.getName());
            model.addAttribute("subscriptions", subscriptionService.findMemberSubscriptions(member.getId()));
        } else {
            model.addAttribute("subscriptions", List.of());
        }
        return "plan/list";
    }

    @GetMapping("/plans/{id}")
    public String detail(@PathVariable Long id, java.security.Principal principal, Model model) {
        // Model 규약: plan
        com.example.sub.domain.entity.SubscriptionPlan plan = subscriptionService.findPlanById(id);
        model.addAttribute("plan", plan);
        if (principal != null) {
            com.example.sub.domain.entity.Member member = memberService.findByEmail(principal.getName());
            java.util.List<com.example.sub.domain.entity.MemberSubscription> subscriptions = subscriptionService.findMemberSubscriptions(member.getId());
            com.example.sub.domain.entity.MemberSubscription userSub = subscriptions.stream()
                    .filter(s -> s.getPlan().getId().equals(id))
                    .findFirst()
                    .orElse(null);
            model.addAttribute("userSub", userSub);
        }
        return "plan/detail";
    }

    @GetMapping({"/recommendation", "/recommendations"})
    public String recommendation(Model model) {
        // 가이드에 따라 recommendations 모델 키를 바인딩 (데이터 없을 시 프론트에서 Fallback 렌더링)
        model.addAttribute("recommendations", java.util.List.of());
        return "recommendation";
    }
}
