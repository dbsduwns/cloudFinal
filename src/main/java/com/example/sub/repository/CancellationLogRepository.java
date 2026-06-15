package com.example.sub.repository;

import com.example.sub.domain.entity.CancellationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CancellationLogRepository extends JpaRepository<CancellationLog, Long> {
    List<CancellationLog> findByMemberId(Long memberId);
}
