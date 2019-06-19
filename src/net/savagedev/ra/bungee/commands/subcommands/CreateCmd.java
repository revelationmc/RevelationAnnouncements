package net.savagedev.ra.bungee.commands.subcommands;

import net.md_5.bungee.api.CommandSender;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.bungee.utils.MessageUtils;
import net.savagedev.ra.common.Sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateCmd implements SubCommand {
    private final RABungee plugin;

    public CreateCmd(RABungee plugin) {
        this.plugin = plugin;
    }

    @Override // create <name> <sound> <messages>
    public void execute(CommandSender user, String... args) {
        if (args.length == 1) {
            MessageUtils.message(user, "&cInvalid arguments! Try: /ra create <name> <sound> <messages>");
            return;
        }

        String name = args[1].toLowerCase();

        if (this.plugin.getAnnouncementManager().exists(name)) {
            MessageUtils.message(user, String.format("&cAn announcement called %s already exists!", name));
            return;
        }

        if (args.length == 2) {
            MessageUtils.message(user, String.format("&cInvalid arguments! Try: /ra create %s <sound> <messages>", name));
            return;
        }

        String soundName = args[2].toUpperCase();

        if (!this.plugin.isValidSound(soundName)) {
            MessageUtils.message(user, "&cInvalid sound!");
            return;
        }

        Sound sound = Sound.valueOf(soundName);

        if (args.length == 3) {
            MessageUtils.message(user, String.format("&cInvalid arguments! Try: /ra create %s %s <messages>", soundName));
            return;
        }

        StringBuilder messageBuilder = new StringBuilder();

        for (int i = 3; i < args.length; i++) {
            messageBuilder.append(args[i]).append(" ");
        }

        List<String> message = Arrays.asList(messageBuilder.toString().trim().split(";"));

        this.plugin.getAnnouncementManager().create(name, message, sound);
        MessageUtils.message(user, "&aAnnouncement successfully created.");
    }

    @Override
    public String getPermission() {
        return "revelation.announcements.create";
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender user, String[] args) {
        if (args.length == 3) {
            List<String> suggestions = new ArrayList<>();

            for (String sound : this.plugin.getSounds()) {
                if (sound.contains(args[2].toUpperCase())) {
                    suggestions.add(sound);
                }
            }

            return suggestions;
        }

        return new ArrayList<>();
    }
}
