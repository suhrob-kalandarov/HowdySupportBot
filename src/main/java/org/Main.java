package org;

import com.pengrad.telegrambot.TelegramBot;
import org.exp.config.DB;
import org.exp.service.BotRunner;
import static org.exp.messages.Constants.BOT_TOKEN;

public class Main {
    public static final TelegramBot bot = new TelegramBot(BOT_TOKEN);
    static {DB.worker();}

    public static void main(String[] args) {
        new BotRunner(bot).run();
    }
}
