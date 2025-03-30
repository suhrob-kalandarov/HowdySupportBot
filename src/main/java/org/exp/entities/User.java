package org.exp.entities;

import com.pengrad.telegrambot.model.Update;
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
@Entity
public class User {
    @Id
    private Long userId;
    private String fullName;
    private String username;
    private String phone;
    private String language;
    private String languageCode;

    private Integer lastMessageId;
    private Boolean isActive;
    private Boolean hasProvidedPhone;

    private LocalDateTime createdAt;


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

        // Ism va familiyani qo'shish
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

    /*
     * Ism, familiya va telegram nomini birlashtirib, fullDisplayName ni o'rnatadi.
     *
     * @param firstName    Foydalanuvchi ismi
     * @param lastName     Foydalanuvchi familiyasi
     * @param telegramName Foydalanuvchi telegram nomi
     */

    /*public void setFullDisplayName(Update update) {
        StringBuilder fullNameBuilder = new StringBuilder();
        String firstName;
        String lastName;
        String telegramName;

        if (update.message()!=null) {
            firstName = update.message().chat().firstName();
            lastName = update.message().chat().lastName();
            telegramName = update.message().chat().username();

        } else {
            firstName = update.callbackQuery().from().firstName();
            lastName = update.callbackQuery().from().lastName();
            telegramName = update.callbackQuery().from().username();
        }

        // Ism va familiyani qo'shish
        if (firstName != null) {
            fullNameBuilder.append(firstName);
        }
        if (lastName != null) {
            if (!fullNameBuilder.isEmpty()) {
                fullNameBuilder.append(" ");
            }
            fullNameBuilder.append(lastName);
        }

        // Telegram nomini qo'shish (agar mavjud bo'lsa)
        if (telegramName != null) {
            if (!fullNameBuilder.isEmpty()) {
                fullNameBuilder.append(" (@").append(telegramName).append(")");
            } else {
                fullNameBuilder.append("@").append(telegramName);
            }
        }

        // fullDisplayName ni o'zgartirish
        this.fullDisplayName = fullNameBuilder.toString();
    }*/
}