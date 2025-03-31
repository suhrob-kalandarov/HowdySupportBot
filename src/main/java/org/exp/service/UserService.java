package org.exp.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.exp.entities.User;
import org.exp.messages.MessageManager;
import org.exp.repos.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.Main.ADMIN_ID;
import static org.exp.messages.MessageManager.installResourceBundle;

public class UserService {
    private static final UserRepository userRepository = UserRepository.getInstance();

    public static User getOrCreateUser(Update update) {
        Long userId;
        Message message = update.message();

        if (message != null) {
            userId = message.from().id();
        } else {
            userId = update.callbackQuery().from().id();
        }

        // var
        Optional<User> optionalUser = (Optional<User>) userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (MessageManager.bundle == null) {
                installResourceBundle(user);
            }
            return user;
        } else {
            User newUser = new User(update);
            newUser.setIsActive(true);
            newUser.setLanguage("en"); // Default til
            newUser.setCreatedAt(LocalDateTime.now());

            userRepository.save(newUser); // Bazaga saqlaymiz

            if (MessageManager.bundle == null) {
                installResourceBundle(newUser);
            }
            return newUser;
        }
    }

    public static boolean isAdmin(Long userId) {
        return userId.equals(ADMIN_ID);
    }
}
