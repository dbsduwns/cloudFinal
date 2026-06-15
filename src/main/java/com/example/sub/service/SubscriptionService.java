package com.example.sub.service;

import com.example.sub.domain.entity.AlertLevel;
import com.example.sub.domain.entity.Member;
import com.example.sub.domain.entity.MemberSubscription;
import com.example.sub.domain.entity.SubscriptionPlan;
import com.example.sub.repository.CancellationLogRepository;
import com.example.sub.domain.entity.CancellationLog;
import com.example.sub.repository.MemberRepository;
import com.example.sub.repository.MemberSubscriptionRepository;
import com.example.sub.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {

    private final SubscriptionPlanRepository planRepository;
    private final MemberSubscriptionRepository memberSubscriptionRepository;
    private final MemberRepository memberRepository;
    private final CancellationLogRepository cancellationLogRepository;

    public List<SubscriptionPlan> findAllPlans() {
        return planRepository.findAll();
    }

    public SubscriptionPlan findPlanById(Long id) {
        return planRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid plan ID"));
    }

    public List<MemberSubscription> findMemberSubscriptions(Long memberId) {
        return memberSubscriptionRepository.findByMemberId(memberId);
    }

    @Transactional
    public void subscribe(Long memberId, Long planId, LocalDate startDate, LocalDate dueDate) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        SubscriptionPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid plan ID"));

        MemberSubscription subscription = MemberSubscription.builder()
                .member(member)
                .plan(plan)
                .startDate(startDate)
                .dueDate(dueDate)
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

        // 해지 다이어리 기록 생성
        CancellationLog log = CancellationLog.builder()
                .member(subscription.getMember())
                .plan(subscription.getPlan())
                .cancelledAt(LocalDate.now())
                .tags("기타")
                .memo("사용 빈도가 낮아 구독 해지")
                .build();
        cancellationLogRepository.save(log);
    }

    @Transactional
    public void subscribe(MemberSubscription subscription) {
        memberSubscriptionRepository.save(subscription);
    }
}
