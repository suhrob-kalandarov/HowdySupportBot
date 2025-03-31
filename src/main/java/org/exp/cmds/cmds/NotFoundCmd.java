package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;

import static org.Main.ADMIN_ID;
import static org.Main.bot;
import static org.exp.messages.Constants.USER_NOT_FOUND_MSG;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class NotFoundCmd implements Command {
    private final Update update;
    private final User user;

    @Override
    public void process() {
        bot.execute(new SendMessage(
                ADMIN_ID, getMessage(USER_NOT_FOUND_MSG).formatted(user.getUserId())
        ));
    }
}
