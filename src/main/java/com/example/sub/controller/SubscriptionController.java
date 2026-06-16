package com.example.sub.controller;

import com.example.sub.service.CheckInService;
import com.example.sub.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final CheckInService checkInService;

    @GetMapping("/subscriptions")
    public String mySubscriptions(Model model) {
        // 실제 운영시는 SecurityContextHolder에서 memberId를 가져와야 함 (임시로 1L 사용)
        Long mockMemberId = 1L;
        
        // Model 규약: subscriptions
        model.addAttribute("subscriptions", subscriptionService.findMemberSubscriptions(mockMemberId));
        return "member/subscriptions";
    }

    @GetMapping("/subscribe")
    public String subscribeForm(@RequestParam(required = false) Long planId, Model model) {
        if (planId == null) {
            return "redirect:/plans";
        }
        model.addAttribute("plan", subscriptionService.findPlanById(planId));
        return "subscribe/form";
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam Long planId, 
                            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().toString()}") String startDate, 
                            RedirectAttributes rttr) {
        // 실제 운영시는 SecurityContextHolder에서 memberId를 가져와야 함
        Long mockMemberId = 1L;
        subscriptionService.subscribe(mockMemberId, planId, LocalDate.parse(startDate));
        
        rttr.addFlashAttribute("successMessage", "구독 신청이 완료되었습니다.");
        return "redirect:/subscribe/result";
    }

    @GetMapping("/subscribe/result")
    public String subscribeResult() {
        return "subscribe/result";
    }

    @PostMapping("/checkin/{id}")
    public String checkIn(@PathVariable Long id, 
                          @RequestParam(required = false) Integer durationMinutes,
                          RedirectAttributes rttr) {
        checkInService.checkIn(id, durationMinutes);
        rttr.addFlashAttribute("successMessage", "체크인이 완료되었습니다.");
        return "redirect:/";
    }

    @PostMapping("/subscriptions/{id}/cancel")
    public String cancel(@PathVariable Long id, RedirectAttributes rttr) {
        subscriptionService.cancelSubscription(id);
        rttr.addFlashAttribute("successMessage", "구독이 해지되었습니다.");
        return "redirect:/subscriptions";
    }
}
