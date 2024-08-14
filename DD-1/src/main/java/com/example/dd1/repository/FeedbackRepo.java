package com.example.dd1.repository;

import com.example.dd1.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
    // Additional query methods if needed
}
