package com.example.sub.dto;

import com.example.sub.domain.entity.MemberSubscription;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsageStats {
    // Basic summary
    private int monthlyTotal;
    private int monthlyChangePct;
    private int activeCount;
    private int expiringCount;
    private int savingsAmount;

    // Advanced dashboard (usage-summary.html)
    private int budgetUsagePct;
    private int nextPaymentDays;
    private MemberSubscription nextSubscription;
    private int totalCount;
    private int avgMonthlyPrice;
    private int savedYtd;
    private int alertCount;

    // Trend chart
    @Builder.Default
    private List<MonthlyTrend> monthlyTrend = new ArrayList<>();

    // Upcoming renewals
    @Builder.Default
    private List<MemberSubscription> upcomingRenewals = new ArrayList<>();

    // AI/Optimization
    private int neglectedCount;
    private int neglectedSavings;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyTrend {
        private String label; // e.g., "1월"
        private int amount;
        private int heightPct; // 0-100 for bar height
        private boolean isCurrent;
    }
}
