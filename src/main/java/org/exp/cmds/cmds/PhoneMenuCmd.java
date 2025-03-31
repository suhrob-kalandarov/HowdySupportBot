package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;

import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.service.ButtonService;

import static org.Main.bot;

@RequiredArgsConstructor
public class PhoneMenuCmd implements Command {
    private final Update update;
    private final User user;

    @Override
    public void process() {
        user.setLastMessageId(bot.execute(
                new SendMessage(user.getUserId(), "Please send phone number!")
                        .replyMarkup(ButtonService.sharePhone(user))
        ).message().messageId());
    }
}
