package net.savagedev.ra.bungee.commands.subcommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.TabExecutor;

public interface SubCommand extends TabExecutor {
    void execute(CommandSender user, String... args);

    String getPermission();
}
