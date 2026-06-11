package com.example.sub.service;

import com.example.sub.domain.entity.MemberSubscription;
import com.example.sub.domain.entity.SubscriptionPlan;
import com.example.sub.repository.MemberSubscriptionRepository;
import com.example.sub.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {

    private final SubscriptionPlanRepository planRepository;
    private final MemberSubscriptionRepository memberSubscriptionRepository;

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
    public void subscribe(MemberSubscription subscription) {
        memberSubscriptionRepository.save(subscription);
    }
}
