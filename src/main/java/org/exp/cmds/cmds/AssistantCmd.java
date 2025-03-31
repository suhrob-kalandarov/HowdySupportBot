package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.messages.Constants;
import org.exp.service.ButtonService;

import static org.Main.bot;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class AssistantCmd implements Command {
    private final Update update;
    private final User user;

    @Override
    public void process() {

        bot.execute(new SendMessage(
                user.getUserId(),
                getMessage(Constants.MAIN_MENU_MSG)
        ).replyMarkup(
                ButtonService.sharePhone(user)
        ));

       /* if (user.getPhone() != null) {
            bot.execute(new SendMessage(
                    user.getUserId(),
                    getMessage(Constants.SECOND_MAIN_MENU_MSG)
            ));

        } else {
            bot.execute(new SendMessage(
                    user.getUserId(),
                    getMessage(Constants.MAIN_MENU_MSG)
            ));
        }*/
    }
}
