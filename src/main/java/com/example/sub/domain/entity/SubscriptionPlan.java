package com.example.sub.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int monthlyPrice;

    @Column(nullable = false)
    private int yearlyPrice;

    @Column(length = 500)
    private String providerUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
}
