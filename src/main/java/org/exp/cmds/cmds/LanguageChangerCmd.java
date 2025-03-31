package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.repos.UserRepository;

import java.util.Locale;

import static org.Main.bot;
import static org.exp.messages.Constants.ISSUE_OR_SUGGESTION_MENU_MSG;
import static org.exp.messages.MessageManager.getMessage;
import static org.exp.messages.MessageManager.setLocale;

@RequiredArgsConstructor
public class LanguageChangerCmd implements Command {
    private final Update update;
    private final User user;
    private final UserRepository userRepository;

    @Override
    public void process() {
        String languageCode = update.callbackQuery().data().split("_")[1];

        System.out.println("Til o'zgartirilmoqda: {" + languageCode +"}");

        user.setLanguage(languageCode);
        setLocale(new Locale(languageCode));

        // ✅ Bazaga saqlash
        userRepository.updateLanguage(user.getUserId(), languageCode);

        EditMessageText editMessageText = new EditMessageText(
                user.getUserId(), user.getLastMessageId(),
                getMessage(ISSUE_OR_SUGGESTION_MENU_MSG)
        );
        SendResponse response = (SendResponse) bot.execute(editMessageText);
        user.setLastMessageId(response.message().messageId());

        System.out.println("Til muvaffaqiyatli o'zgartirildi: {" + user.getLanguage() +"}");
    }
}
