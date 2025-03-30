package org.exp.service;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.exp.cmds.cmds.CabinetCmd;
import org.exp.cmds.cmds.ForwardMessageCmd;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.messages.Constants;
import org.exp.messages.MessageManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static org.Main.ADMIN_ID;
import static org.Main.bot;
import static org.exp.messages.Constants.*;
import static org.exp.messages.MessageManager.*;

public class UpdateService {

    // Forward qilingan xabarlarni bog‘lash uchun HashMap
    public static final Map<Integer, Long> forwardedMessages = new HashMap<>();

    public static void handle(Update update) {
        try {
            if (update.message() != null) {

                Command command = null;

                Message message = update.message();
                String text = message.text();
                long chatId = message.chat().id();
                User user = UserService.getOrCreateUser(update, chatId);

                if (MessageManager.bundle == null) {
                    installResourceBundle(user);
                }

                if (message.contact() != null) {
                    String phone = message.contact().phoneNumber();
                    if (phone != null) {
                        user.setPhone(phone);
                        user.setHasProvidedPhone(true);
                        /*bot.execute(new SendMessage(
                                user.getUserId(),
                                getMessage(PHONE_SUCCESS_MSG)
                                        +"\n\n" + getMessage(ISSUE_OR_SUGGESTION_MENU_MSG)
                        ));*/

                        bot.execute(new SendMessage(chatId,
                                getMessage(PHONE_SUCCESS_MSG)
                                +"\n\n" + getMessage(ISSUE_OR_SUGGESTION_MENU_MSG)
                        ).replyMarkup(new ReplyKeyboardRemove()));

                    }
                }

                else if (text.equals("/start")){
                    new CabinetCmd(update, user).process();

                } else if (text.equals("/lang")) { //&& !isAdmin(chatId)
                    user.setLastMessageId(
                            bot.execute(new SendMessage(
                                            user.getUserId(),
                                    getMessage(LANG_CHANGE_MENU_MSG)
                                    ).replyMarkup(ButtonService.langMenuBtns())
                            ).message().messageId()
                    );

                } else if (text.equals("/info")) {

                    /// change

                    user.setLastMessageId(
                            bot.execute(new SendMessage(
                                    user.getUserId(), INFO_MSG
                                    ).replyMarkup(ButtonService.infoMenuButtons())
                            ).message().messageId()
                    );

                } else if (text.equals("/phone")) {
                    user.setLastMessageId(bot.execute(
                            new SendMessage(user.getUserId(), "Please send phone number!")
                                    .replyMarkup(ButtonService.sharePhone())
                    ).message().messageId());
                }



                // 1️⃣ Foydalanuvchi botga yozsa, xabar adminlarga forward qilinadi
                else if (!isAdmin(user.getUserId())) {
                    command = new ForwardMessageCmd(update, user);
                }



                // 2️⃣ Agar admin reply qilsa
                else if (message.replyToMessage() != null) {

                    if (!user.getHasProvidedPhone()){
                        //bot.execute(new EditMessageText(user.getUserId(), user.getMessageId(), "↩"));
                        bot.execute(new SendMessage(chatId, "↩")
                                .replyMarkup(new ReplyKeyboardRemove()));
                    } else {
                        bot.execute(
                                new EditMessageText(
                                        user.getUserId(), user.getLastMessageId(),
                                        "<b>↩</b>"
                                ).parseMode(ParseMode.HTML)
                        );
                    }

                    Integer repliedMessageId = message.replyToMessage().messageId();
                    Long userId = forwardedMessages.get(repliedMessageId);

                    if (userId != null) {
                        SendMessage replyToUser = new SendMessage(userId, "📩: " + message.text());
                        bot.execute(replyToUser);

                    } else {
                        bot.execute(new SendMessage(ADMIN_ID, getMessage(USER_NOT_FOUND_MSG).formatted(user.getUserId())));
                    }


                    
                } else {
                    bot.execute(new SendMessage(
                            ADMIN_ID, Constants.REPLY_TO_MSG
                    ));
                }
                Objects.requireNonNull(command).process();



            } else if (update.callbackQuery() != null) {
                CallbackQuery callbackQuery = update.callbackQuery();
                String data = callbackQuery.data();
                long chatId = callbackQuery.from().id();
                User user = UserService.getOrCreateUser(update, chatId);

                if (data.startsWith("lang_")) {

                    String languageCode = data.split("_")[1];

                    System.out.println("Til o'zgartirilmoqda: {" + languageCode +"}");

                    user.setLanguage(languageCode);
                    setLocale(new Locale(languageCode));

                    EditMessageText editMessageText = new EditMessageText(
                            user.getUserId(), user.getLastMessageId(),
                            getMessage(ISSUE_OR_SUGGESTION_MENU_MSG)
                    );
                    SendResponse response = (SendResponse) bot.execute(editMessageText);
                    user.setLastMessageId(response.message().messageId());
                    System.out.println("Til muvaffaqiyatli o'zgartirildi: {" + user.getLanguage() +"}");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAdmin(long chatId) {
        return chatId == ADMIN_ID;
    }
}
