package com.devrezaur.main.repository;

import com.devrezaur.main.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    Page<Feedback> findByFeedbackForContainingIgnoreCase(String feedbackFor, Pageable pageable);

    Page<Feedback> findByFeedbackBy(String feedbackBy, Pageable pageable);
}