package org.exp.cmds.cmds;

import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import org.exp.faces.Command;
import org.exp.repos.ForwardedMessageRepository;

@RequiredArgsConstructor
public class RespondCmd implements Command {
    private final Message message;

    @Override
    public void process() {
        Command command = null;
        ForwardedMessageRepository forwardRepo = ForwardedMessageRepository.getInstance();
        Integer repliedMessageId = message.replyToMessage().messageId();
        Long userId = (Long) forwardRepo.findById(repliedMessageId);

        if (userId != null) {
            command = new SendRespondCmd(message, userId);
        } else {
            command = new NotFoundUserCmd(message.from().id() + " | " + userId);
        }

        command.process();
    }
}
