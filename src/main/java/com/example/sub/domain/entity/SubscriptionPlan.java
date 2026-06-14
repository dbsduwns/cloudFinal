package com.example.sub.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subscription_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private Integer monthlyPrice;
    private Integer yearlyPrice;
    private String providerUrl;
}
