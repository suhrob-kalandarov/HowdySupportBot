package org.exp.cmds.hands;

import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;

import org.exp.cmds.cmds.*;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.faces.Handle;
import org.exp.messages.MessageManager;
import org.exp.repos.ForwardedMessageRepository;
import org.exp.repos.UserRepository;
import org.exp.service.UserService;

import static org.exp.messages.Constants.*;
import static org.exp.messages.MessageManager.installResourceBundle;

@RequiredArgsConstructor
public class MessageCmd implements Handle {
    private final Update update;
    private final User user;
    private final UserRepository userRepository;
    private final ForwardedMessageRepository forwardedMessageRepository;

    @Override
    public void handle() {
        try {
            Command command = null;
            Message message = update.message();

            if (message == null) return;

            String text = message.text();
            Contact contact = message.contact();
            boolean isAdmin = UserService.isAdmin(user.getUserId());

            if (MessageManager.bundle == null) {
                installResourceBundle(user);
            }

            if (isAdmin && message.replyToMessage() != null) {
                Integer repliedMessageId = message.replyToMessage().messageId();
                Long userId = (Long) forwardedMessageRepository.findById(repliedMessageId);

                command = (userId != null) ? new RespondCmd(update, userId) : new NotFoundCmd(update, user);

            } else if (contact != null) {
                command = new ContactCmd(user, contact, userRepository);

            } else if (SLASH_START.equals(text) || text.equals(SLASH_HELP)) {
                command = new CabinetCmd(user);

            } else if (SLASH_LANG.equals(text)) {
                command = new LanguageMenuCmd(update, user);

            } else if (SLASH_INFO.equals(text)) {
                command = new InfoMenuCmd(update, user);

            } else if (!isAdmin) {
                command = new ForwardMessageCmd(update, user, forwardedMessageRepository);

            } else {
                command = new WarningMenuCmd(update, user);
            }

            command.process();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
