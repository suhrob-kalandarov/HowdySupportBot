package org.exp.cmds.cmds;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.faces.Command;

import static org.Main.bot;
import static org.exp.messages.Constants.ADMIN_ID;
import static org.exp.messages.Constants.USER_NOT_FOUND_MSG;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class NotFoundCmd implements Command {
    private final Long userId;

    @Override
    public void process() {
        bot.execute(new SendMessage(
                ADMIN_ID, getMessage(USER_NOT_FOUND_MSG).formatted(userId)
        ));
    }
}
