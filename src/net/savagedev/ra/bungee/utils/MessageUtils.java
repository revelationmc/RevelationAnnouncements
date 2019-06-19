package net.savagedev.ra.bungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageUtils {
    private MessageUtils() {
    }

    public static void message(CommandSender user, String message) {
        message(user, new TextComponent(color(message)));
    }

    private static void message(CommandSender user, TextComponent textComponent) {
        user.sendMessage(textComponent);
    }

    private static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
