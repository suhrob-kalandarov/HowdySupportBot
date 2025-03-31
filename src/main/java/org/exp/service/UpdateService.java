package org.exp.service;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import org.exp.cmds.hands.CallbackQueryCmd;
import org.exp.cmds.hands.MessageCmd;
import org.exp.entities.User;
import org.exp.faces.Handle;
import org.exp.repos.ForwardedMessageRepository;
import org.exp.repos.UserRepository;

import java.util.Objects;

public class UpdateService {
    private static final UserRepository userRepository = UserRepository.getInstance();
    private static final ForwardedMessageRepository forwardedMessageRepository = ForwardedMessageRepository.getInstance();

    public static void handle(Update update) {
        try {
            Handle handle = null;
            Message message = update.message();
            CallbackQuery callbackQuery = update.callbackQuery();
            User user = UserService.getOrCreateUser(update);

            if (message != null) {
                handle = new MessageCmd(update, user, userRepository, forwardedMessageRepository);

            } else if (callbackQuery != null) {
                handle = new CallbackQueryCmd(update, user, userRepository);
            }

            Objects.requireNonNull(handle).handle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
