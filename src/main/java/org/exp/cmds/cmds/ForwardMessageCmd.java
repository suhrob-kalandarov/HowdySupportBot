package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.repos.ForwardedMessageRepository;
import org.exp.service.UserService;

import static org.Main.ADMIN_ID;
import static org.Main.bot;
import static org.exp.messages.Constants.MSG_NOT_SENT_MSG;
import static org.exp.messages.Constants.REQUEST_SUBMITTED_MSG;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class ForwardMessageCmd implements Command {
    private final Update update;
    private final User user;
    private final ForwardedMessageRepository forwardedMessageRepository;

    @Override
    public void process() {
        Message message = update.message();
        try {
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
        }


        String userInfo = userInfoBuilder(user);


        if (!user.getUserId().equals(ADMIN_ID)){
            user.setLastMessageId(bot.execute(new SendMessage(
                    ADMIN_ID,
                    "📩Admin you have new message!\n" + userInfo
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
            bot.execute(new SendMessage(ADMIN_ID, getMessage(MSG_NOT_SENT_MSG)));
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
        return "\n🆔 From (ID): " + user.getUserId() +
                "\n👤 Display: " + user.getFullName() +
                "\n📞 Phone: " + (user.getPhone() != null ? user.getPhone() : "❌ Not provided") +
                "\n🌍 Language: " + user.getLanguage() +
                "\n📩 Message:\uD83D\uDC47";
    }
}
