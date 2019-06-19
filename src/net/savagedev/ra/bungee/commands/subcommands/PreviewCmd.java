package net.savagedev.ra.bungee.commands.subcommands;

import net.md_5.bungee.api.CommandSender;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.bungee.utils.MessageUtils;
import net.savagedev.ra.common.Announcement;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PreviewCmd implements SubCommand {
    private final RABungee plugin;

    public PreviewCmd(RABungee plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender user, String... args) {
        if (args.length == 1) {
            MessageUtils.message(user, "&cInvalid arguments! Try: /ra preview <name>");
            return;
        }

        String name = args[1].toLowerCase();

        if (!this.plugin.getAnnouncementManager().exists(name)) {
            MessageUtils.message(user, String.format("&cAn announcement by the name of %s does not exist.", name));
            return;
        }

        Announcement announcement = this.plugin.getAnnouncementManager().get(name);
        this.plugin.getMessageManager().send(announcement);
    }

    @Override
    public String getPermission() {
        return "revelation.announcements.preview";
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender user, String[] args) {
        if (args.length == 2) {
            String name = args[1].toLowerCase();

            List<String> suggestions = new ArrayList<>();

            for (String announcement : this.plugin.getAnnouncementManager().getAll().keySet()) {
                if (announcement.startsWith(name)) {
                    suggestions.add(announcement);
                }
            }

            return suggestions;
        }

        return new ArrayList<>();
    }
}
