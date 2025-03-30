package org.exp.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.exp.entities.User;
import org.exp.messages.MessageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.Main.ADMIN_ID;
import static org.Main.bot;
import static org.exp.messages.MessageManager.installResourceBundle;

public interface UserService {
    List<User> USERS_LIST = new ArrayList<>();

    static User getOrCreateUser(Update update, long userId) {
        Optional<User> optionalUser = USERS_LIST.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (MessageManager.bundle == null) {
                installResourceBundle(user);
            }
            return user;

        } else {
            User user = new User();
            user.setUserId(userId);
            user.setUsername(update.message().chat().username());
            user.setFullName(update);
            user.setIsActive(true);
            user.setHasProvidedPhone(false);
            user.setLanguage("en");
            USERS_LIST.add(user);
            if (MessageManager.bundle == null) {
                installResourceBundle(user);
            }
            return user;
        }
    }

    static void sendMessageToUser(User user) {
        StringBuilder message = new StringBuilder();

        message.append("ID:").append(user.getUserId());
        message.append("\nName: ").append(user.getFullName());
        message.append("\nUsername: @").append(user.getUsername());
        message.append("\nPhone: ").append(user.getPhone());
        message.append("\nLanguage: ").append(user.getLanguage());
        message.append("\nActive: ").append(user.getIsActive());

        if (!user.getUserId().equals(ADMIN_ID)){
            user.setLastMessageId(bot.execute(new SendMessage(
                    ADMIN_ID,
                    "New user joined to support bot!\n\n" + message
            )).message().messageId());
        }
    }

    /*static void sendMessageToUserChangeLang(User user) {
        StringBuilder message = new StringBuilder();

        message.append("ID:").append(user.getUserId());
        message.append("\nDisplay: ").append(user.getFullDisplayName());
        message.append("\nPhone: ").append(user.getPhoneNumber());
        message.append("\nLanguage: ").append(user.getLanguage()).append("(changed)");
        message.append("\nActive: ").append(user.getIsActive());

        if (!user.getUserId().equals(ADMIN_ID)){
            bot.execute(new EditMessageText(
                    ADMIN_ID, user.getMessageId(),
                    "New user joined to support bot!\n\n" + message
            ));
        }
    }*/


/*    static User getOrCreateUser(Update update) {
        User user = null;

        if (update.callbackQuery() != null) {
            //user = getUserFromDatabase(update.callbackQuery().from().id());

            if (user!=null){
               user.setFullName(getUserFullName(update));
               user.setUsername(update.callbackQuery().from().username());
                //Objects.requireNonNull(user).setGameBoard(getGameBoard(user.getUserId()));
            }
            return user;
        }

        Long userId = update.message().from().id();
        String username = update.message().from().username();

        //user = getUserFromDatabase(userId);

        user = User.builder()
                .userId(userId)
                .fullName(getUserFullName(update))
                .username(update.message().chat().username())
                .messageId(null)
                //.userState(UserState.START)
                //.language("en")
                .build();
        *//*insertUserIntoDatabase(user);
        insertDefaultGameStatus(userId);
        user.setGameBoard(getGameBoard(user.getUserId()));
        insertGames(userId);*//*

        return user;
    }*/
}
