package com.example.sub.repository;

import com.example.sub.domain.entity.MemberSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberSubscriptionRepository extends JpaRepository<MemberSubscription, Long> {
    List<MemberSubscription> findByMemberId(Long memberId);
}
