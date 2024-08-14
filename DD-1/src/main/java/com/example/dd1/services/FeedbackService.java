package com.example.dd1.services;

import com.example.dd1.entity.Feedback;
import com.example.dd1.repository.FeedbackRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepository;

    public Feedback createFeedback(String productName, String feedbackText, Integer rating) {
        Feedback feedback = new Feedback();
        feedback.setProductName(productName);
        feedback.setFeedback(feedbackText);
        feedback.setRating(rating);

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
    
    public long countFeedbacks() {
        return feedbackRepository.count();
    }

}
