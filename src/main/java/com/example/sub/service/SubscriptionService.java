package com.example.sub.service;

import com.example.sub.domain.entity.AlertLevel;
import com.example.sub.domain.entity.Member;
import com.example.sub.domain.entity.MemberSubscription;
import com.example.sub.domain.entity.SubscriptionPlan;
import com.example.sub.domain.entity.SubscriptionUsage;
import com.example.sub.repository.MemberRepository;
import com.example.sub.repository.MemberSubscriptionRepository;
import com.example.sub.repository.SubscriptionPlanRepository;
import com.example.sub.repository.SubscriptionUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {

    private final SubscriptionPlanRepository planRepository;
    private final MemberSubscriptionRepository memberSubscriptionRepository;
    private final MemberRepository memberRepository;
    private final SubscriptionUsageRepository usageRepository;

    public List<SubscriptionPlan> findAllPlans() {
        return planRepository.findAll();
    }

    public SubscriptionPlan findPlanById(Long id) {
        return planRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid plan ID"));
    }

    public List<MemberSubscription> findMemberSubscriptions(Long memberId) {
        List<MemberSubscription> subscriptions = memberSubscriptionRepository.findByMemberId(memberId);
        LocalDate today = LocalDate.now();
        LocalDate startOfHeatmap = today.minusDays(29);
        LocalDate startOfMonth = today.withDayOfMonth(1);

        for (MemberSubscription sub : subscriptions) {
            List<SubscriptionUsage> usages = usageRepository.findByMemberSubscriptionIdAndUsedDateBetween(sub.getId(), startOfHeatmap, today);
            
            long checkInCount = usages.stream()
                    .filter(u -> !u.getUsedDate().isBefore(startOfMonth) && u.isUsed())
                    .count();
            sub.setMonthlyCheckInCount((int) checkInCount);
            
            int[] heatmap = new int[30];
            for (int i = 0; i < 30; i++) {
                LocalDate date = startOfHeatmap.plusDays(i);
                boolean wasUsed = usages.stream()
                        .filter(u -> u.getUsedDate().equals(date))
                        .map(SubscriptionUsage::isUsed)
                        .findFirst()
                        .orElse(false);
                heatmap[i] = wasUsed ? 1 : 0;
            }
            sub.setHeatmapData(Arrays.toString(heatmap));
            
            if (sub.getDueDate() != null) {
                long days = java.time.temporal.ChronoUnit.DAYS.between(today, sub.getDueDate());
                sub.setDaysUntilDue((int) days);
            }
        }
        return subscriptions;
    }

    @Transactional
    public void subscribe(Long memberId, Long planId, LocalDate startDate) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        SubscriptionPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid plan ID"));

        MemberSubscription subscription = MemberSubscription.builder()
                .member(member)
                .plan(plan)
                .startDate(startDate)
                .status("ACTIVE")
                .lastUsedAt(startDate)
                .alertLevel(AlertLevel.NORMAL)
                .build();

        memberSubscriptionRepository.save(subscription);
    }

    @Transactional
    public void cancelSubscription(Long subscriptionId) {
        MemberSubscription subscription = memberSubscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid subscription ID"));
        subscription.setStatus("CANCELLED");
    }

    @Transactional
    public void subscribe(MemberSubscription subscription) {
        memberSubscriptionRepository.save(subscription);
    }
}
