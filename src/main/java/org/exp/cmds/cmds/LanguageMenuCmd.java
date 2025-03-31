package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.service.ButtonService;

import static org.Main.bot;
import static org.exp.messages.Constants.LANG_CHANGE_MENU_MSG;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class LanguageMenuCmd implements Command {
    private final Update update;
    private final User user;

    @Override
    public void process() {
        user.setLastMessageId(
                bot.execute(new SendMessage(
                                user.getUserId(), getMessage(LANG_CHANGE_MENU_MSG)
                        ).replyMarkup(ButtonService.langMenuBtns())
                ).message().messageId()
        );
    }
}
