package com.example.sub.controller;

import com.example.sub.repository.CancellationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CancellationLogController {

    private final CancellationLogRepository cancellationLogRepository;

    @GetMapping("/cancellation-log")
    public String logList(Model model) {
        // 임시 mockMemberId = 1L
        Long mockMemberId = 1L;

        // Model Key: cancellationLogs
        model.addAttribute("cancellationLogs", cancellationLogRepository.findByMemberId(mockMemberId));
        return "cancellation-log";
    }
}
