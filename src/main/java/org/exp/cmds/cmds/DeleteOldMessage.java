package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;

import static org.Main.bot;

@RequiredArgsConstructor
public class DeleteOldMessage implements Command {
    private final Update update;
    private final User user;

    @Override
    public void process() {

        if (user.getPhone()==null){
            //bot.execute(new EditMessageText(user.getUserId(), user.getMessageId(), "↩"));
            bot.execute(new SendMessage(user.getUserId(), "↩")
                    .replyMarkup(new ReplyKeyboardRemove())
            );

        } else {
            bot.execute(
                    new EditMessageText(
                            user.getUserId(), user.getLastMessageId(), "<b>↩</b>"
                    ).parseMode(ParseMode.HTML)
            );
        }
    }
}
