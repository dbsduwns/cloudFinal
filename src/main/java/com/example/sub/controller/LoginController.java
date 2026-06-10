package com.example.sub.controller;

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

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("memberForm") MemberForm memberForm, 
                       BindingResult bindingResult, 
                       Model model) {
        
        // Password mismatch check
        if (memberForm.getPassword() != null && !memberForm.getPassword().equals(memberForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        // Mock success, redirect to login
        return "redirect:/login";
    }
}
