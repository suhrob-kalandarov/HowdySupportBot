package org.exp.cmds.cmds;

import com.pengrad.telegrambot.response.SendResponse;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import org.exp.faces.Command;
import org.exp.repos.UserRepository;

import static org.Main.bot;
import static org.exp.messages.Constants.*;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class SendRespondCmd implements Command {
    private final Message message;
    private final Long userId;

    @Override
    public void process() {
        UserRepository userRepo = UserRepository.getInstance();
        SendResponse response = bot.execute(
                new SendMessage(userId, "📩You have respond:\uD83D\uDC47\n\n" + message.text())
        );

        if (response.isOk()) {
            bot.execute(new SendMessage(ADMIN_ID,
                    getMessage(SUCCESS_SENT_MSG).formatted(userId))
            );
            if (!userRepo.isUserActive(userId)){
                userRepo.updateUserActive(userId, true);
            }
        } else {
            bot.execute(new SendMessage(ADMIN_ID,
                    getMessage(NOT_SENT_MSG).formatted(userId))
            );
            userRepo.updateUserActive(userId, false);
        }
    }
}
