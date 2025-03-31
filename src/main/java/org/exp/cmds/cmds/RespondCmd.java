package org.exp.cmds.cmds;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;

import org.exp.entities.User;
import org.exp.faces.Command;

import static org.Main.bot;

@RequiredArgsConstructor
public class RespondCmd implements Command {
    private final Update update;
    private final Long userId;

    @Override
    public void process() {
        Message message = update.message();
        SendMessage replyToUser = new SendMessage(
                userId,
                "📩You have respond:\uD83D\uDC47\n\n" + message.text()
        );
        bot.execute(replyToUser);
    }
}
