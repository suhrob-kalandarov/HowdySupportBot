package org.exp.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

public class BotRunner implements Runnable {
    private final TelegramBot bot;

    public BotRunner(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                UpdateService.handle(update);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
