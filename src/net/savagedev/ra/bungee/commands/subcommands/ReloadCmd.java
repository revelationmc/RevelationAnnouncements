package net.savagedev.ra.bungee.commands.subcommands;

import net.md_5.bungee.api.CommandSender;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.bungee.utils.MessageUtils;

import java.util.ArrayList;

public class ReloadCmd implements SubCommand {
    private final RABungee plugin;

    public ReloadCmd(RABungee plugin) {
        this.plugin = plugin;
    }

    @Override // reload
    public void execute(CommandSender user, String... args) {
        this.plugin.reload();
        MessageUtils.message(user, "&aPlugin reloaded.");
    }

    @Override
    public String getPermission() {
        return "revelation.announcements.reload";
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender user, String[] args) {
        return new ArrayList<>();
    }
}
