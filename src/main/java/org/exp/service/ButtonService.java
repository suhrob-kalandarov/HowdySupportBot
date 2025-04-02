package org.exp.service;

import com.pengrad.telegrambot.model.request.*;
import org.exp.entities.User;
import org.exp.messages.Constants;

import static org.exp.messages.Constants.*;

public interface ButtonService {
    static InlineKeyboardMarkup langMenuBtns() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(LANG_EN_BTN).callbackData(LANG + "en"),
                new InlineKeyboardButton(LANG_RU_BTN).callbackData(LANG + "ru"),
                new InlineKeyboardButton(LANG_UZ_BTN).callbackData(LANG + "uz")
        );
    }

    static Keyboard sharePhone(User user) {
        if (user.getPhone() == null) {  // Agar foydalanuvchida telefon yo'q bo'lsa
            return new ReplyKeyboardMarkup(
                    new KeyboardButton[]{
                            new KeyboardButton(Constants.SHARE_PHONE_BTN).requestContact(true)
                    }
            ).resizeKeyboard(true).oneTimeKeyboard(true);
        }
        return new ReplyKeyboardMarkup(new KeyboardButton[][]{});
    }

    static InlineKeyboardMarkup infoMenuButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(
                new InlineKeyboardButton(PLATFORM_TG_CHANNEL).url(PLATFORM_CHANNEL_LINK),
                new InlineKeyboardButton(BOTS_CHANNEL).url(BOTS_CHANNEL_LINK)
        );

        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(PLATFORM_SITE).url(PLATFORM_SITE_LINK)
        );

        return inlineKeyboardMarkup;
    }
}
