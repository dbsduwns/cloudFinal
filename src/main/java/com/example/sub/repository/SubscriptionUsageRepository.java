package com.example.sub.repository;

import com.example.sub.domain.entity.SubscriptionUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface SubscriptionUsageRepository extends JpaRepository<SubscriptionUsage, Long> {
    List<SubscriptionUsage> findByMemberSubscriptionIdAndUsedDateBetween(Long subscriptionId, LocalDate start, LocalDate end);
}
