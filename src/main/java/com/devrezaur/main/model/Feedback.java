package com.devrezaur.main.model;

import com.devrezaur.main.config.StringCryptoConverter; // <-- IMPORT THIS
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "feedback_table")
public class Feedback {
    @Id
    @GeneratedValue
    private UUID feedbackId;
    private String feedbackFor;

    @Convert(converter = StringCryptoConverter.class)
    private String feedbackBy;

    private String title;
    @Lob
    private String message;
    private Boolean isPositive;
    private LocalDateTime createdAt;
}