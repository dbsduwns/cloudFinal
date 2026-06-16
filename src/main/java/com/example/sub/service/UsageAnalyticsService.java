package com.example.sub.service;

import com.example.sub.dto.UsageStats;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UsageAnalyticsService {

    public UsageStats getUsageStats(Long memberId) {
        // Dummy data for Frontend 2 to see the layout
        return UsageStats.builder()
                .monthlyTotal(45800)
                .monthlyChangePct(-12)
                .activeCount(4)
                .expiringCount(1)
                .savingsAmount(8900)
                .budgetUsagePct(75)
                .nextPaymentDays(3)
                .totalCount(5)
                .avgMonthlyPrice(11200)
                .savedYtd(124000)
                .alertCount(1)
                .neglectedCount(1)
                .neglectedSavings(9900)
                .monthlyTrend(Arrays.asList(
                        UsageStats.MonthlyTrend.builder().label("1월").amount(32000).heightPct(60).build(),
                        UsageStats.MonthlyTrend.builder().label("2월").amount(35000).heightPct(65).build(),
                        UsageStats.MonthlyTrend.builder().label("3월").amount(48000).heightPct(90).build(),
                        UsageStats.MonthlyTrend.builder().label("4월").amount(42000).heightPct(80).build(),
                        UsageStats.MonthlyTrend.builder().label("5월").amount(45800).heightPct(85).isCurrent(true).build()
                ))
                .build();
    }
}
