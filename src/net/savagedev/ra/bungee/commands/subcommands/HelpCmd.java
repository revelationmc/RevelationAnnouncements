package net.savagedev.ra.bungee.commands.subcommands;

import net.md_5.bungee.api.CommandSender;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.bungee.utils.MessageUtils;

import java.util.ArrayList;

public class HelpCmd implements SubCommand {
    private final RABungee plugin;

    public HelpCmd(RABungee plugin) {
        this.plugin = plugin;
    }

    @Override // help
    public void execute(CommandSender user, String... args) {
        MessageUtils.message(user, "/ra help - This help page.");
        MessageUtils.message(user, "/ra reload - Reloads the plugin.");
        MessageUtils.message(user, "/ra list - Lists all announcements.");
        MessageUtils.message(user, "/ra delay [delay] - Shows the plugins delay.");
        MessageUtils.message(user, "/ra edit <name> <sound/message> <[line]/sound> [message] - Edit an announcement.");
        MessageUtils.message(user, "/ra create <name> <sound> <message> - Create an announcement.");
        MessageUtils.message(user, "/ra preview <name> - Preview an announcement.");
        MessageUtils.message(user, "/ra delete <name> - Delete an announcement.");
    }

    @Override
    public String getPermission() {
        return "revelation.announcements.help";
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender user, String[] args) {
        return new ArrayList<>();
    }
}
