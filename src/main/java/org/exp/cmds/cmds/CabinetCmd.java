package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;

import static org.Main.bot;
import static org.exp.messages.Constants.MAIN_MENU_MSG;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class CabinetCmd implements Command {
    private final Update update;
    private final User user;

    @Override
    public void process() {
        bot.execute(new SendMessage(
                user.getUserId(), //user.getMessageId(),
                getMessage(MAIN_MENU_MSG)
        ));
    }
}
