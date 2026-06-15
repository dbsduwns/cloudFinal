package com.example.sub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.sub.dto.MemberForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid email or password.");
        }
        return "login";
    }
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        // If error is present, th:if="${param.error}" in Thymeleaf will be triggered
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Simple demo credentials check
        if ("test@example.com".equals(username) && "password123".equals(password)) {
            return "redirect:/member/mypage";
        }
        // Redirect with error query parameter if login fails
        return "redirect:/login?error";
    }

}
