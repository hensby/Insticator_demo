package com.challenge.demo.repository;

import com.challenge.demo.entity.QuestionsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsHistoryRepository extends JpaRepository<QuestionsHistory, Long> {
}
