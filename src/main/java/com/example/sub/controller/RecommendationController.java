package com.example.sub.controller;

import com.example.sub.domain.entity.MemberSubscription;
import com.example.sub.domain.entity.SubscriptionPlan;
import com.example.sub.service.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecommendationController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/recommendations")
    public String recommendList(Model model) {
        Long mockMemberId = 1L;
        List<MemberSubscription> mySubs = subscriptionService.findMemberSubscriptions(mockMemberId);
        List<SubscriptionPlan> allPlans = subscriptionService.findAllPlans();

        List<RecommendationDto> recommendations = new ArrayList<>();

        // 사용자가 구독 중인 상품이 있을 때 대체 추천 Mocking 데이터 생성
        if (!mySubs.isEmpty() && allPlans.size() > 1) {
            MemberSubscription currentSub = mySubs.get(0);
            SubscriptionPlan suggestedPlan = allPlans.stream()
                    .filter(p -> !p.getId().equals(currentSub.getPlan().getId()))
                    .findFirst()
                    .orElse(allPlans.get(0));

            int currentPrice = currentSub.getPlan().getMonthlyPrice();
            int suggestedPrice = suggestedPlan.getMonthlyPrice();
            int monthlySavings = Math.max(0, currentPrice - suggestedPrice);
            int expectedSavings = monthlySavings * 12;

            recommendations.add(RecommendationDto.builder()
                    .currentSubscription(currentSub)
                    .suggestedPlan(suggestedPlan)
                    .reason(monthlySavings > 0 ? "월 요금 " + monthlySavings + "원 절약 가능!" : "사용 패턴 대비 더 풍성한 혜택 제공")
                    .expectedSavings(expectedSavings > 0 ? expectedSavings : 12000)
                    .build());
        }

        model.addAttribute("recommendations", recommendations);
        return "recommendation";
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendationDto {
        private MemberSubscription currentSubscription;
        private SubscriptionPlan suggestedPlan;
        private String reason;
        private int expectedSavings;
    }
}
