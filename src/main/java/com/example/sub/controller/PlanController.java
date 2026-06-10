package com.example.sub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class PlanController {

    /**
     * [프론트엔드 3] 플랜 목록 (/) 
     * Model Key: plans
     */
    @GetMapping("/")
    public String planList(Model model) {
        List<Map<String, Object>> plans = new ArrayList<>();
        
        // 1. 정상 상태 (Netflix)
        plans.add(createMockSubscription(1L, "Netflix", "OTT", "NORMAL", 24, false));
        // 2. 경고 상태 (Youtube)
        plans.add(createMockSubscription(2L, "YouTube Premium", "Video", "WARNING", 12, true));
        // 3. 위험 상태 (Spotify)
        plans.add(createMockSubscription(3L, "Spotify", "Music", "DANGER", 3, false));
        // 4. 해지 권고 (Adobe)
        plans.add(createMockSubscription(4L, "Adobe CC", "Design", "CRITICAL", 0, false));

        model.addAttribute("plans", plans);
        return "plan/list";
    }

    /**
     * [프론트엔드 3] 플랜 상세 (/plans/{id})
     * Model Key: plan, subscription, usageStats
     */
    @GetMapping("/plans/{id}")
    public String planDetail(@PathVariable Long id, Model model) {
        // 플랜 정보
        model.addAttribute("plan", Map.of(
            "id", id,
            "name", "테스트 프리미엄 서비스",
            "category", "OTT",
            "monthlyPrice", 14900,
            "yearlyPrice", 178800,
            "providerUrl", "https://netflix.com"
        ));
        
        // 구독 정보
        model.addAttribute("subscription", Map.of(
            "id", 1L,
            "startDate", LocalDate.now().minusMonths(6),
            "dueDate", LocalDate.now().plusDays(10),
            "lastUsedAt", LocalDate.now().minusDays(3),
            "alertLevel", "NORMAL"
        ));

        // 오늘 체크인 완료 여부
        model.addAttribute("todayCheckedIn", false);

        // 히트맵용 상세 사용 통계 (30일치)
        List<Map<String, Object>> usageStats = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            usageStats.add(Map.of(
                "usedDate", LocalDate.now().minusDays(i),
                "used", random.nextBoolean()
            ));
        }
        model.addAttribute("usageStats", usageStats);
        
        return "plan/detail";
    }

    private Map<String, Object> createMockSubscription(Long id, String name, String category, String alertLevel, int usageCount, boolean todayCheckedIn) {
        List<Boolean> recentUsage = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            recentUsage.add(random.nextInt(10) > 7); // 30% 확률로 사용함 표시
        }

        return Map.of(
            "id", id,
            "plan", Map.of("id", id, "name", name, "category", category),
            "alertLevel", alertLevel,
            "monthlyUsageCount", usageCount,
            "recentUsage", recentUsage,
            "todayCheckedIn", todayCheckedIn
        );
    }
}
