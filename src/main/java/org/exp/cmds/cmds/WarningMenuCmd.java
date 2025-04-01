package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;

import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.messages.Constants;

import static org.Main.bot;
import static org.exp.messages.Constants.ADMIN_ID;

@RequiredArgsConstructor
public class WarningMenuCmd implements Command {
    private final Update update;
    private final User user;

    @Override
    public void process() {
        if (!user.getUserId().equals(ADMIN_ID)){
            bot.execute(new SendMessage(
                    user.getUserId(), "Warning!"
            ));
        } else {
            bot.execute(new SendMessage(
                    ADMIN_ID, Constants.REPLY_TO_MSG
            ));
        }
    }
}
