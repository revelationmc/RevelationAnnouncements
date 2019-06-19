package net.savagedev.ra.bungee.utils;

import net.md_5.bungee.BungeeCord;

import java.util.logging.Level;

public class DebugLogger {
    private static boolean debugging = false;

    private DebugLogger() {
    }

    public static void log(String message) {
        if (debugging) {
            BungeeCord.getInstance().getLogger().log(Level.INFO, message);
        }
    }

    public static void toggle() {
        debugging = !debugging;
    }

    public static boolean isDebugging() {
        return debugging;
    }
}
