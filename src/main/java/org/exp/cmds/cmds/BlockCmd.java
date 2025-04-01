package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import lombok.RequiredArgsConstructor;
import org.exp.entities.User;
import org.exp.faces.Command;
import org.exp.repos.UserRepository;

import static org.Main.bot;
import static org.exp.messages.Constants.ADMIN_ID;

@RequiredArgsConstructor
public class BlockCmd implements Command {
    private final User user;
    private final String data;

    @Override
    public void process() {
        UserRepository userRepo = UserRepository.getInstance();
        Long userId =Long.parseLong(data.split("_")[1]);

        if (!user.getIsBlocked()){
            userRepo.setUserBlocked(userId, true);
            bot.execute(new EditMessageText(ADMIN_ID, user.getLastMessageId(), "User blocked!"));

        } else {
            userRepo.setUserBlocked(userId, false);
            bot.execute(new EditMessageText(ADMIN_ID, user.getLastMessageId(), "User unblocked!"));
        }
    }
}
