package com.example.sub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController {

    /**
     * [프론트엔드 3] 마이페이지 (/member/mypage)
     * Model Key: member, usageStats, subscriptions
     */
    @GetMapping("/member/mypage")
    public String mypage(Model model) {
        // 1. member 객체
        model.addAttribute("member", Map.of(
            "name", "홍길동",
            "email", "test@example.com",
            "createdAt", LocalDateTime.now().minusMonths(6),
            "role", "USER"
        ));

        // 2. usageStats 객체
        model.addAttribute("usageStats", Map.of(
            "monthlyTotal", 29800,
            "monthlyChangePct", -12.5,
            "activeCount", 2,
            "expiringCount", 1,
            "savingsAmount", 15000
        ));

        // 3. subscriptions 리스트
        List<Map<String, Object>> subscriptions = new ArrayList<>();
        
        subscriptions.add(Map.of(
            "id", 1L,
            "plan", Map.of(
                "id", 1L,
                "name", "Netflix",
                "category", "OTT",
                "monthlyPrice", 14900
            ),
            "startDate", LocalDate.now().minusMonths(3),
            "status", "ACTIVE",
            "monthlyCheckInCount", 18
        ));

        subscriptions.add(Map.of(
            "id", 2L,
            "plan", Map.of(
                "id", 2L,
                "name", "Disney+",
                "category", "OTT",
                "monthlyPrice", 9900
            ),
            "startDate", LocalDate.now().minusMonths(1),
            "status", "CANCELLED",
            "monthlyCheckInCount", 2
        ));

        model.addAttribute("subscriptions", subscriptions);

        return "member/mypage";
    }

    /**
     * [프론트엔드 3] 마이페이지 정보 수정 폼 데모
     */
    @GetMapping("/member/update")
    public String updateForm() {
        // 실구현 대신 마이페이지로 임시 리다이렉트
        return "redirect:/member/mypage";
    }

    /**
     * [프론트엔드 3] 구독 해지 처리 데모 (GET)
     */
    @GetMapping("/subscriptions/{id}/cancel")
    public String cancelSubscriptionDemo(@PathVariable Long id) {
        return "redirect:/member/mypage";
    }

    /**
     * [프론트엔드 3] 구독 재활성화 처리 데모 (GET)
     */
    @GetMapping("/subscriptions/{id}/reactivate")
    public String reactivateSubscriptionDemo(@PathVariable Long id) {
        return "redirect:/member/mypage";
    }
}
