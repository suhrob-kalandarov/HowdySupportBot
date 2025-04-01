package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.repos.ForwardedMessageRepository;
import org.exp.service.ButtonService;

import static org.Main.bot;
import static org.exp.messages.Constants.*;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class ForwardMessageCmd implements Command {
    private final Update update;
    private final User user;
    private final ForwardedMessageRepository forwardedMessageRepository;

    @Override
    public void process() {
        Message message = update.message();
        /*try {
            if (user.getPhone() != null && UserService.isAdmin(user.getUserId())){
                //bot.execute(new EditMessageText(user.getUserId(), user.getLastMessageId(), "↩"));

                bot.execute(new SendMessage(user.getUserId(), "↩")
                        .replyMarkup(new ReplyKeyboardRemove()));
            } else {
                bot.execute(
                        new EditMessageText(
                                user.getUserId(), user.getLastMessageId(),
                                "<b>↩</b>"
                        ).parseMode(ParseMode.HTML)
                );
            }

        } catch (Exception e) {
            System.err.println("Xabarini edit qilishda xatolik: {" + e.getMessage() + "}, {" + e +"}");
        }*/


        String userInfo = userInfoBuilder(user);

        if (!user.getUserId().equals(ADMIN_ID)){
            user.setLastMessageId(bot.execute(new SendMessage(
                    ADMIN_ID,
                    "📩Admin you have new message!\n" + userInfo
            ).replyMarkup(
                    ButtonService.blockUserBtn(user)
            )).message().messageId());
        }

        ForwardMessage forward = new ForwardMessage(ADMIN_ID, user.getUserId(), message.messageId());
        SendResponse response = bot.execute(forward);

        // Forward qilingan xabar ID'sini saqlash
        if (response.isOk()) {
            forwardedMessageRepository.save(response.message().messageId(), user.getUserId());

            bot.execute(new SendMessage(
                    user.getUserId(), getMessage(REQUEST_SUBMITTED_MSG)
            ));

        } else {
           // bot.execute(new SendMessage(ADMIN_ID, getMessage(NOT_SENT_MSG)));
        }


        /*ForwardMessage forward = new ForwardMessage(ADMIN_ID, user.getUserId(), message.messageId());
        SendResponse response = bot.execute(forward);

        // Forward qilingan xabar ID'sini saqlash
        if (response.isOk()) {
            forwardedMessages.put(response.message().messageId(), user.getUserId());
            bot.execute(new SendMessage(
                    user.getUserId(), getMessage(REQUEST_SUBMITTED_MSG)
            ));

        } else {
            bot.execute(new SendMessage(ADMIN_ID, getMessage(MSG_NOT_SENT_MSG)));
        }*/
    }

    private String userInfoBuilder(User user) {
        return isBlocked(user) + user.getUserId() +
                "\n🆔: " + user.getUserId() +
                "\n👤: " + user.getFullName() +
                "\n📞: " + (user.getPhone() != null ? user.getPhone() : "❌ Not provided") +
                "\n🌍: " + user.getLanguage() +
                "\n📩\uD83D\uDC47";
    }

    private String isBlocked(User user) {
        if (user.getIsBlocked()) {
            return "\n❌BLOCKED❌";
        }
        return "";
    }
}
