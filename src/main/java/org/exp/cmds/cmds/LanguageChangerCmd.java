package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.CallbackQuery;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.repos.UserRepository;

import java.util.Locale;
import static org.exp.messages.MessageManager.setLocale;

@RequiredArgsConstructor
public class LanguageChangerCmd implements Command {
    private final User user;
    private final CallbackQuery callbackQuery;

    @Override
    public void process() {
        UserRepository userRepository = UserRepository.getInstance();
        String languageCode = callbackQuery.data().split("_")[1];

        System.out.println("Til o'zgartirilmoqda: {" + languageCode +"}");

        user.setLanguage(languageCode);
        setLocale(new Locale(languageCode));

        // ✅ Bazaga saqlash
        userRepository.updateLanguage(user.getUserId(), languageCode);

        /*EditMessageText editMessageText = new EditMessageText(
                user.getUserId(), user.getLastMessageId(),
                getMessage(LANG_SUCCESS_MSG) + "\n\n" + getMessage(ISSUE_OR_SUGGESTION_MENU_MSG)
        );
        bot.execute(editMessageText);*/
        //new DeleteOldMessage(user).process();
        new CabinetCmd(user).process();

        System.out.println("Til muvaffaqiyatli o'zgartirildi: {" + user.getLanguage() +"}");
    }
}
