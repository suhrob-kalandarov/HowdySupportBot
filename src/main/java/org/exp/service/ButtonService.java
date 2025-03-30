package org.exp.service;

import com.pengrad.telegrambot.model.request.*;
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

    static ReplyKeyboardMarkup sharePhone() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton(Constants.SHARE_PHONE_BTN).requestContact(true)
        ).resizeKeyboard(true).oneTimeKeyboard(true);
    }

    static InlineKeyboardMarkup infoMenuButtons() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(MAIN_CHANNEL).url(MAIN_CHANNEL_LINK)
        );
    }
}
