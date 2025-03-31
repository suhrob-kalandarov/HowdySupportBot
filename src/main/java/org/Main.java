package org;

import com.pengrad.telegrambot.TelegramBot;
import org.exp.config.DB;
import org.exp.service.BotRunner;

public class Main {
    public static final String BOT_TOKEN = "7653674551:AAGwYuapIQsRk4y-OzVTPlj71XnXgTzzlFs"; // ⚠️ Tokenni yashiring!
    public static final long ADMIN_ID = 6513286717L;
    public static final TelegramBot bot = new TelegramBot(BOT_TOKEN);

    static {
        DB.worker();
    }

    public static void main(String[] args) {
        new BotRunner(bot).run(); // ✅ BotRunner ishga tushiriladi
    }
}
