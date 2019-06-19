package net.savagedev.ra.bungee.commands.subcommands;

import net.md_5.bungee.api.CommandSender;
import net.savagedev.ra.bungee.utils.DebugLogger;
import net.savagedev.ra.bungee.utils.MessageUtils;

import java.util.ArrayList;

public class DebugCmd implements SubCommand {
    @Override
    public void execute(CommandSender user, String... args) {
        DebugLogger.toggle();
        MessageUtils.message(user, String.format("7Debugging %s.", (DebugLogger.isDebugging() ? "enabled" : "disabled")));
    }

    @Override
    public String getPermission() {
        return "revelation.announcements.debug";
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender user, String[] args) {
        return new ArrayList<>();
    }
}
