package com.example.sub.controller;

import com.example.sub.domain.entity.MemberSubscription;
import com.example.sub.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlanController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/")
    public String list(Model model) {
        // 홈 화면: 내가 구독 중인 목록을 보여줌 (plan/list.html)
        Long mockMemberId = 1L;
        model.addAttribute("plans", subscriptionService.findMemberSubscriptions(mockMemberId));
        return "plan/list";
    }

    @GetMapping("/plans")
    public String allPlans(Model model) {
        // 전체 서비스 플랜 템플릿 목록 (plan/all.html)
        model.addAttribute("plans", subscriptionService.findAllPlans());
        return "plan/all";
    }

    @GetMapping("/plans/{id}")
    public String detail(@PathVariable Long id, Model model) {
        // Model 규약: plan
        model.addAttribute("plan", subscriptionService.findPlanById(id));

        // 사용자의 활성화된 구독이 있는지 조회하여 detail.html에 연동
        Long mockMemberId = 1L;
        List<MemberSubscription> mySubs = subscriptionService.findMemberSubscriptions(mockMemberId);
        MemberSubscription mySub = mySubs.stream()
                .filter(s -> s.getPlan().getId().equals(id) && "ACTIVE".equals(s.getStatus()))
                .findFirst()
                .orElse(null);
        model.addAttribute("subscription", mySub);

        // 오늘 체크인 여부
        boolean todayCheckedIn = false;
        if (mySub != null && mySub.getLastUsedAt() != null) {
            todayCheckedIn = mySub.getLastUsedAt().isEqual(LocalDate.now());
        }
        model.addAttribute("todayCheckedIn", todayCheckedIn);

        // 최근 30일치 사용 기록 (detail.html: usageStats)
        model.addAttribute("usageStats", new java.util.ArrayList<>());

        return "plan/detail";
    }
}
