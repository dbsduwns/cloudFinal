package com.example.sub.service;

import com.example.sub.domain.entity.AlertLevel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class AlertLevelResolver {

    public AlertLevel resolve(LocalDate lastUsedAt) {
        if (lastUsedAt == null) return AlertLevel.CRITICAL;

        long daysBetween = ChronoUnit.DAYS.between(lastUsedAt, LocalDate.now());

        if (daysBetween < 7) return AlertLevel.NORMAL;
        if (daysBetween < 14) return AlertLevel.WARNING;
        if (daysBetween < 30) return AlertLevel.DANGER;
        return AlertLevel.CRITICAL;
    }
}
