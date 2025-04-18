package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.model.Contact;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.repos.UserRepository;
import static org.Main.bot;
import static org.exp.messages.Constants.*;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class ContactCmd implements Command {
    private final User user;
    private final Contact contact;
    private final UserRepository userRepository;

    @Override
    public void process() {
        String phone = contact.phoneNumber();

        if (phone != null) {
            phone = phonePlusChecker(phone);
            user.setPhone(phone);
            userRepository.updatePhoneNumber(user.getUserId(), user.getPhone());

            bot.execute(new SendMessage(user.getUserId(),
                    getMessage(PHONE_SUCCESS_MSG) + "\n\n" + getMessage(ISSUE_OR_SUGGESTION_MENU_MSG)
            ).replyMarkup(
                    new ReplyKeyboardRemove()
            ));

        } else {
            System.out.println("Phone null: user{" + user.getUserId() + "}");
        }
    }

    private String phonePlusChecker(String phone) {
        boolean containsPlus = contact.phoneNumber().contains("+");

        if (!containsPlus) {
            return "+" + phone;
        }
        return phone;
    }
}