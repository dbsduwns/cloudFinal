package com.example.sub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UsageAnalyticsController {

    /**
     * [프론트엔드 2] 사용 패턴 요약 화면 (/member/usage-summary)
     * Model Key: member, usageStats
     */
    @GetMapping("/member/usage-summary")
    public String usageSummary(Model model) {
        // 1. member 객체
        model.addAttribute("member", Map.of(
            "name", "홍길동"
        ));

        // 2. monthlyTrend 리스트 (최근 6개월 지출 트렌드)
        List<Map<String, Object>> monthlyTrend = new ArrayList<>();
        monthlyTrend.add(Map.of("label", "1월", "amount", 19800, "heightPct", 40, "isCurrent", false));
        monthlyTrend.add(Map.of("label", "2월", "amount", 29800, "heightPct", 60, "isCurrent", false));
        monthlyTrend.add(Map.of("label", "3월", "amount", 34800, "heightPct", 70, "isCurrent", false));
        monthlyTrend.add(Map.of("label", "4월", "amount", 44800, "heightPct", 90, "isCurrent", false));
        monthlyTrend.add(Map.of("label", "5월", "amount", 39800, "heightPct", 80, "isCurrent", false));
        // 이번 달 (6월) 현재 데이터
        monthlyTrend.add(Map.of("label", "6월", "amount", 29800, "heightPct", 60, "isCurrent", true));

        // 3. upcomingRenewals 리스트 (갱신 예정 구독들)
        List<Map<String, Object>> upcomingRenewals = new ArrayList<>();
        upcomingRenewals.add(Map.of(
            "plan", Map.of("name", "Disney+", "category", "OTT", "monthlyPrice", 9900),
            "daysUntilDue", 2
        ));
        upcomingRenewals.add(Map.of(
            "plan", Map.of("name", "Netflix", "category", "OTT", "monthlyPrice", 14900),
            "daysUntilDue", 5
        ));
        upcomingRenewals.add(Map.of(
            "plan", Map.of("name", "Youtube Premium", "category", "Video", "monthlyPrice", 14900),
            "daysUntilDue", 12
        ));

        // 4. usageStats 객체
        java.util.Map<String, Object> usageStats = java.util.Map.ofEntries(
            java.util.Map.entry("monthlyTotal", 29800),
            java.util.Map.entry("monthlyChangePct", -25.1), // 지난 달 대비 25.1% 감소
            java.util.Map.entry("budgetUsagePct", 59), // 예산 50,000원 대비 59% 사용 가정
            java.util.Map.entry("nextPaymentDays", 2), // 2일 후 결제 예정
            java.util.Map.entry("nextSubscription", java.util.Map.of(
                "plan", java.util.Map.of("name", "Disney+", "monthlyPrice", 9900),
                "dueDate", LocalDate.now().plusDays(2)
            )),
            java.util.Map.entry("totalCount", 3),
            java.util.Map.entry("avgMonthlyPrice", 13233),
            java.util.Map.entry("savedYtd", 45000), // 올해 절약한 누적 금액
            java.util.Map.entry("alertCount", 1), // 경고 상태인 구독 개수
            java.util.Map.entry("monthlyTrend", monthlyTrend),
            java.util.Map.entry("upcomingRenewals", upcomingRenewals),
            java.util.Map.entry("neglectedCount", 1), // 방치 구독 개수 (1개)
            java.util.Map.entry("neglectedSavings", 14900) // 해지 시 절감할 수 있는 금액
        );

        model.addAttribute("usageStats", usageStats);

        return "member/usage-summary";
    }
}
