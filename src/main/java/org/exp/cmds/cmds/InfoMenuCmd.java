package org.exp.cmds.cmds;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;

import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.service.ButtonService;

import static org.Main.bot;
import static org.exp.messages.Constants.INFO_MSG;

@RequiredArgsConstructor
public class InfoMenuCmd implements Command {
    private final Update update;
    private final User user;

    @Override
    public void process() {
        /// change
        user.setLastMessageId(
                bot.execute(new SendMessage(
                                user.getUserId(), INFO_MSG
                        ).replyMarkup(ButtonService.infoMenuButtons())
                ).message().messageId()
        );
    }
}
