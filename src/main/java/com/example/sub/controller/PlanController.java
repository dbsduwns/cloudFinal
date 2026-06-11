package com.example.sub.controller;

import com.example.sub.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PlanController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/")
    public String list(Model model) {
        // Model 규약: plans
        model.addAttribute("plans", subscriptionService.findAllPlans());
        return "plan/list";
    }

    @GetMapping("/plans/{id}")
    public String detail(@PathVariable Long id, Model model) {
        // Model 규약: plan
        model.addAttribute("plan", subscriptionService.findPlanById(id));
        return "plan/detail";
    }
}
