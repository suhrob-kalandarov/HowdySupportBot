package org.exp.cmds.cmds;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.service.UserService;
import org.exp.service.ButtonService;
import static org.Main.bot;
import static org.exp.messages.Constants.MAIN_MENU_MSG;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class CabinetCmd implements Command {
    private final User user;

    @Override
    public void process() {
        if (UserService.isAdmin(user.getUserId())) {
            bot.execute(new SendMessage(user.getUserId(), "You're Admin!"));
        }

        SendMessage message = new SendMessage(
                user.getUserId(),
                getMessage(MAIN_MENU_MSG)
        );

        message.replyMarkup(ButtonService.sharePhone(user));

        bot.execute(message);
    }
}
