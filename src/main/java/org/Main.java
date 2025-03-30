package org;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import org.exp.service.UpdateService;

public class Main {
    public static final String BOT_TOKEN = "7653674551:AAGwYuapIQsRk4y-OzVTPlj71XnXgTzzlFs";
    public static final long ADMIN_ID = 6513286717L; // Admin ID ni o'zgartiring
    public static final TelegramBot bot = new TelegramBot(BOT_TOKEN);

    public static void main(String[] args) {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                UpdateService.handle(update);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}

