package com.example.sub.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class SubscriptionUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_subscription_id", nullable = false)
    private MemberSubscription memberSubscription;

    @Column(nullable = false)
    private LocalDate usedDate;

    @Column(nullable = false)
    private boolean used = false;

    private Integer durationMinutes;

    private LocalDateTime createdAt = LocalDateTime.now();
}
