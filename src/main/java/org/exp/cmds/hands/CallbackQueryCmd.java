package org.exp.cmds.hands;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.exp.cmds.cmds.*;
import org.exp.entities.User;
import org.exp.faces.*;
import org.exp.repos.UserRepository;

import java.util.Objects;

import static org.exp.messages.Constants.LANG;

@RequiredArgsConstructor
public class CallbackQueryCmd implements Handle {
    private final Update update;
    private final User user;
    private final UserRepository userRepository;

    @Override
    public void handle() {
        CallbackQuery callbackQuery = update.callbackQuery();

        Command command = null;

        String data = callbackQuery.data();

        if (data.startsWith(LANG)) {
            command = new LanguageChangerCmd(user, callbackQuery);

        } else if (data.startsWith("block_") || data.startsWith("unblock_")) {
            //command = new BlockCmd(user, data);

        } else {
            command = new WarningMenuCmd(update, user);
        }
        Objects.requireNonNull(command).process();
    }
}
