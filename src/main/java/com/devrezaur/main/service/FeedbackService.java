package com.devrezaur.main.service;

import com.devrezaur.main.model.Feedback;
import com.devrezaur.main.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public Page<Feedback> getFeedbacks(Pageable pageable) {
        Page<Feedback> page = feedbackRepository.findAll(pageable);
        return page.map(this::truncateMessage);
    }

    // Updated to return Page
    public Page<Feedback> searchFeedbacksByName(String feedbackFor, Pageable pageable) {
        Page<Feedback> page = feedbackRepository.findByFeedbackForContainingIgnoreCase(feedbackFor, pageable);
        return page.map(this::truncateMessage);
    }

    // Updated to return Page
    public Page<Feedback> searchFeedbacksByUser(String feedbackBy, Pageable pageable) {
        Page<Feedback> page = feedbackRepository.findByFeedbackBy(feedbackBy, pageable);
        return page.map(this::truncateMessage);
    }

    public Feedback getFeedbackById(UUID feedbackId) {
        return feedbackRepository.findById(feedbackId).orElse(null);
    }

    public void deleteFeedbackById(UUID feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }


    private Feedback truncateMessage(Feedback feedback) {
        String message = feedback.getMessage();
        if (message != null && message.length() > 200) {
            feedback.setMessage(message.substring(0, 200) + " …");
        }
        return feedback;
    }
}