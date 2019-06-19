package net.savagedev.ra.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.bungee.commands.subcommands.*;
import net.savagedev.ra.bungee.utils.MessageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevelationAnnouncementsCmd extends Command implements TabExecutor {
    private final Map<String, SubCommand> subCommands;
    private final RABungee plugin;

    public RevelationAnnouncementsCmd(RABungee plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);

        this.subCommands = new HashMap<>();
        this.plugin = plugin;
        this.init();
    }

    private void init() {
        this.subCommands.putIfAbsent("create", new CreateCmd(this.plugin));
        this.subCommands.putIfAbsent("debug", new DebugCmd());
        this.subCommands.putIfAbsent("delay", new DelayCmd(this.plugin));
        this.subCommands.putIfAbsent("delete", new DeleteCmd(this.plugin));
        this.subCommands.putIfAbsent("edit", new EditCmd(this.plugin));
        this.subCommands.putIfAbsent("help", new HelpCmd(this.plugin));
        this.subCommands.putIfAbsent("list", new ListCmd(this.plugin));
        this.subCommands.putIfAbsent("preview", new PreviewCmd(this.plugin));
        this.subCommands.putIfAbsent("reload", new ReloadCmd(this.plugin));
    }

    @Override
    public void execute(CommandSender user, String... args) {
        if (args.length == 0) {
            MessageUtils.message(user, "&cInvalid arguments! Try: /ra help");
            return;
        }

        String commandStr = args[0].toLowerCase();

        if (!this.subCommands.containsKey(commandStr)) {
            MessageUtils.message(user, String.format("&cInvalid arguments! Try: /ra %s", this.getSuggestion(user, commandStr)));
            return;
        }

        SubCommand command = this.subCommands.get(commandStr);

        if (!user.hasPermission(command.getPermission())) {
            MessageUtils.message(user, "&cYou do not have permission to execute this command!");
            return;
        }

        command.execute(user, args);
    }

    private String getSuggestion(CommandSender user, String partialArg) {
        for (String command : this.subCommands.keySet()) {
            if (command.startsWith(partialArg) && user.hasPermission(this.subCommands.get(command).getPermission())) {
                return command;
            }
        }

        return "help";
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender user, String... args) {
        if (args.length == 0) {
            return this.subCommands.keySet();
        }

        String commandStr = args[0].toLowerCase();

        if (!this.subCommands.containsKey(commandStr) && args.length == 1) {
            List<String> suggestions = new ArrayList<>();

            for (String command : this.subCommands.keySet()) {
                if (command.startsWith(commandStr)) {
                    suggestions.add(command);
                }
            }

            return suggestions;
        }

        SubCommand command = this.subCommands.get(commandStr);

        if (command == null) {
            return new ArrayList<>();
        }

        return command.onTabComplete(user, args);
    }
}
