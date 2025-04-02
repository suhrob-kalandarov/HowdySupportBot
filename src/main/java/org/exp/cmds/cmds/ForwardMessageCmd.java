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

import static org.Main.bot;
import static org.exp.messages.Constants.*;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class ForwardMessageCmd implements Command {
    private final Update update;
    private final User user;

    @Override
    public void process() {
        Message message = update.message();
        String userInfo = userInfoBuilder(user);
        ForwardedMessageRepository forwardRepo = ForwardedMessageRepository.getInstance();

        if (!user.getUserId().equals(ADMIN_ID)){
            user.setLastMessageId(bot.execute(new SendMessage(
                    ADMIN_ID,
                    "📩Admin you have new message!\n" + userInfo
            )).message().messageId());
        }

        ForwardMessage forward = new ForwardMessage(ADMIN_ID,
                user.getUserId(), message.messageId()
        ).protectContent(true);
        SendResponse response = bot.execute(forward);

        // Forward qilingan xabar ID'sini saqlash va foydalanuvchiga xabar berish!
        if (response.isOk()) {
            forwardRepo.save(response.message().messageId(), user.getUserId());
            bot.execute(new SendMessage(
                    user.getUserId(), getMessage(REQUEST_SUBMITTED_MSG)
            ));
        } else {
           bot.execute(new SendMessage(user.getUserId(), getMessage(USER_NOT_SENT_MSG)));
        }
    }

    private String userInfoBuilder(User user) {
        return "\n🆔: " + user.getUserId() +
                "\n👤: " + user.getFullName() +
                "\n📞: " + (user.getPhone() != null ? user.getPhone() : "❌ Not provided") +
                "\n🌍: " + user.getLanguage() +
                "\n📩\uD83D\uDC47";
    }
}
