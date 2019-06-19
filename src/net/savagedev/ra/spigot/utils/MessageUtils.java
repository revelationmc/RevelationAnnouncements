package net.savagedev.ra.spigot.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageUtils {
    public static void message(CommandSender user, String message) {
        user.sendMessage(color(message));
    }

    public static void broadcast(List<String> messages) {
        broadcast(messages, null);
    }

    public static void broadcast(List<String> messages, Sound sound) {
        for (Player user : Bukkit.getOnlinePlayers()) {
            for (String message : messages) {
                message(user, message);
            }

            if (sound != null) {
                user.playSound(user.getLocation(), sound, 1F, 1F);
            }
        }
    }

    private static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
