package com.example.sub.controller;

import com.example.sub.service.CheckInService;
import com.example.sub.service.SubscriptionService;
import com.example.sub.service.UsageStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final CheckInService checkInService;
    private final UsageStatisticsService usageStatisticsService;

    @GetMapping("/subscriptions")
    public String mySubscriptions(Model model) {
        // 실제 운영시는 SecurityContextHolder에서 memberId를 가져와야 함 (임시로 1L 사용)
        Long mockMemberId = 1L;
        
        // Model 규약: subscriptions
        model.addAttribute("subscriptions", subscriptionService.findMemberSubscriptions(mockMemberId));
        return "member/subscriptions";
    }

    @PostMapping("/subscribe")
    public String subscribe(Long planId, RedirectAttributes rttr) {
        // 구독 신청 로직 처리 (내부적으로 SubscriptionService 호출)
        rttr.addFlashAttribute("successMessage", "구독 신청이 완료되었습니다.");
        return "redirect:/subscriptions";
    }

    @PostMapping("/checkin/{id}")
    public String checkIn(@PathVariable Long id, RedirectAttributes rttr) {
        checkInService.checkIn(id);
        rttr.addFlashAttribute("successMessage", "체크인이 완료되었습니다.");
        return "redirect:/";
    }
}
