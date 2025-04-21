package org.exp.cmds.cmds;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.repos.UserRepository;
import org.exp.service.ButtonService;
import static org.Main.bot;
import static org.exp.messages.Constants.MAIN_MENU_MSG;
import static org.exp.messages.MessageManager.getMessage;

@RequiredArgsConstructor
public class CabinetCmd implements Command {
    private final User user;

    @Override
    public void process() {
        UserRepository userRepo = UserRepository.getInstance();

        if (user.getLastMessageId() != null){
            new DeleteOldMessage(user).process();
        }

        user.setLastMessageId(bot.execute(
                new SendMessage(user.getUserId(),
                        getMessage(MAIN_MENU_MSG)
                ).replyMarkup(ButtonService.sharePhone(user))
        ).message().messageId());
        userRepo.updateLastMessageId(user.getUserId(), user.getLastMessageId());
    }
}
