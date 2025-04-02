package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;

import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.repos.UserRepository;
import org.exp.service.ButtonService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.Main.bot;
import static org.exp.messages.Constants.INFO_MENU_MSG;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class InfoMenuCmd implements Command {
    private final User user;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void process() {
        new DeleteOldMessage(user).process();

        UserRepository userRepo = UserRepository.getInstance();
        Message sentMessage = bot.execute(new SendMessage(
                        user.getUserId(), getMessage(INFO_MENU_MSG)
                ).replyMarkup(ButtonService.infoMenuButtons())
        ).message();

        user.setTimerMessageId(sentMessage.messageId());
        userRepo.setTimerMessageId(user.getUserId(), user.getTimerMessageId());
        /// 2 daqiqadan keyin xabarni o'zgartirish uchun scheduler ishlatamiz
        scheduleEditMessage(user.getUserId(), user.getTimerMessageId());
    }

    private void scheduleEditMessage(Long userId, Integer messageId) {
        scheduler.schedule(() -> {
            bot.execute(new EditMessageText(userId, messageId, "↩"));
            new CabinetCmd(user).process();
        }, 15, TimeUnit.SECONDS);
    }
}
