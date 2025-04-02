package org.exp.entities;

import com.pengrad.telegrambot.model.Update;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "phone")
    private String phone;

    @Column(name = "language")
    private String language;

    @Column(name = "last_message_id")
    private Integer lastMessageId;

    @Column(name = "timer_message_id")
    private Integer timerMessageId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();


    public User(Update update){
        this.userId = update.message().from().id();
        this.username = update.message().chat().username();
        this.fullName = setFullName(update);
    }

    public String setFullName(Update update) {
        AtomicReference<StringBuilder> fullNameBuilder = new AtomicReference<>(new StringBuilder());
        String firstName;
        String lastName;

        if (update.message()!=null) {
            firstName = update.message().chat().firstName();
            lastName = update.message().chat().lastName();
        } else {
            firstName = update.callbackQuery().from().firstName();
            lastName = update.callbackQuery().from().lastName();
        }

        /// Adding firstName and lastName
        if (firstName != null) {
            fullNameBuilder.get().append(firstName);
        }
        if (lastName != null) {
            if (!fullNameBuilder.get().isEmpty()) {
                fullNameBuilder.get().append(" ");
            }
            fullNameBuilder.get().append(lastName);
        }
        return fullNameBuilder.toString();
    }
}