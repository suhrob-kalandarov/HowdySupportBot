package org.exp.messages;

import org.exp.entities.User;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    public static ResourceBundle bundle;

    public static void loadBundle(String baseName, Locale locale) {
        bundle = ResourceBundle.getBundle(baseName, locale);
    }

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    public static String getMessage(String key) {
        return bundle.getString(key);
    }

    // ResourceBundle ni yuklash
    public static void installResourceBundle(User user) {
        System.out.println("ResourceBundle yuklanmagan! Qayta yuklashga harakat qilamiz.");
        MessageManager.loadBundle(
                "messages",
                new Locale(user.getLanguage())
        );
        System.out.println("ResourceBundle yuklandi! user=" + user.getUserId());
    }
}