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
public class MemberSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private SubscriptionPlan plan;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate dueDate;

    @Column(nullable = false)
    private String status = "ACTIVE";

    private LocalDate lastUsedAt;

    @Enumerated(EnumType.STRING)
    private AlertLevel alertLevel = AlertLevel.NORMAL;

    private LocalDateTime createdAt = LocalDateTime.now();
}
