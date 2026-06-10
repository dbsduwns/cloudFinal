package com.example.sub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SubscriptionController {

    /**
     * [프론트엔드 3] 내 구독 관리 (/subscriptions)
     * Model Key: subscriptions
     */
    @GetMapping("/subscriptions")
    public String mySubscriptions(Model model) {
        List<Map<String, Object>> subscriptions = new ArrayList<>();
        
        subscriptions.add(Map.of(
            "id", 1L,
            "plan", Map.of("id", 1L, "name", "Netflix", "category", "OTT"),
            "startDate", LocalDate.now().minusMonths(3),
            "dueDate", LocalDate.now().plusDays(15),
            "lastUsedAt", LocalDate.now().minusDays(2),
            "status", "ACTIVE",
            "alertLevel", "NORMAL"
        ));
        
        subscriptions.add(Map.of(
            "id", 2L,
            "plan", Map.of("id", 2L, "name", "Disney+", "category", "OTT"),
            "startDate", LocalDate.now().minusMonths(1),
            "dueDate", LocalDate.now().plusDays(5),
            "lastUsedAt", LocalDate.now().minusDays(10),
            "status", "ACTIVE",
            "alertLevel", "WARNING"
        ));

        model.addAttribute("subscriptions", subscriptions);
        return "member/subscriptions";
    }

    @GetMapping("/subscribe")
    public String subscribeForm(@org.springframework.web.bind.annotation.RequestParam(required = false) Long planId, Model model) {
        Long id = (planId != null) ? planId : 1L;
        model.addAttribute("plan", Map.of(
            "id", id,
            "name", id == 1L ? "Netflix" : "Disney+",
            "monthlyPrice", 14900
        ));
        return "subscribe/form";
    }

    /**
     * [프론트엔드 3] 대체 추천 (/recommendations)
     * Model Key: recommendations
     */
    @GetMapping("/recommendations")
    public String recommendations(Model model) {
        List<Map<String, Object>> recommendations = new ArrayList<>();
        
        recommendations.add(Map.of(
            "currentSubscription", Map.of(
                "plan", Map.of("id", 1L, "name", "고가 OTT", "monthlyPrice", 17000),
                "monthlyUsageCount", 1
            ),
            "suggestedPlan", Map.of("id", 2L, "name", "저가형 OTT", "monthlyPrice", 5500),
            "reason", "최근 사용량이 적어 저가형 플랜으로 변경을 추천합니다.",
            "expectedSavings", 11500 * 12
        ));

        model.addAttribute("recommendations", recommendations);
        return "recommendation";
    }

    @GetMapping("/cancellation-log")
    public String cancellationLog(Model model) {
        List<Map<String, Object>> cancellationLogs = new ArrayList<>();
        
        cancellationLogs.add(Map.of(
            "id", 1L,
            "plan", Map.of("name", "YouTube Premium"),
            "cancelledAt", LocalDate.now().minusDays(5),
            "tags", "비쌈,대체제있음",
            "memo", "광고 제거 기능은 좋았으나 요즘 넷플릭스를 주로 봐서 해지했습니다."
        ));
        
        cancellationLogs.add(Map.of(
            "id", 2L,
            "plan", Map.of("name", "Spotify"),
            "cancelledAt", LocalDate.now().minusDays(20),
            "tags", "안씀,단순변심",
            "memo", "최근 음악을 유튜브로 많이 들어서 스포티파이는 구독 중단합니다."
        ));

        model.addAttribute("cancellationLogs", cancellationLogs);
        return "cancellation-log";
    }

    /**
     * [프론트엔드 3] 오늘 체크인 처리
     * API: POST /checkin/{id}
     */
    @PostMapping("/checkin/{id}")
    public String checkIn(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", "체크인이 완료되었습니다!");
        return "redirect:/";
    }

    /**
     * [프론트엔드 3] 구독 결과 화면 테스트용 GET
     */
    @GetMapping("/subscribe/result")
    public String subscribeResult(Model model) {
        model.addAttribute("isSuccess", true);
        model.addAttribute("message", "구독 신청이 임시 완료되었습니다! (테스트 화면)");
        return "subscribe/result";
    }
}
