package net.savagedev.ra.bungee.commands.subcommands;

import net.md_5.bungee.api.CommandSender;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.bungee.utils.MessageUtils;
import net.savagedev.ra.common.Announcement;

import java.util.ArrayList;

public class ListCmd implements SubCommand {
    private final RABungee plugin;

    public ListCmd(RABungee plugin) {
        this.plugin = plugin;
    }

    @Override // list
    public void execute(CommandSender user, String... args) {
        for (String name : this.plugin.getAnnouncementManager().getAll().keySet()) {
            Announcement announcement = this.plugin.getAnnouncementManager().get(name);

            MessageUtils.message(user, String.format("&6%s&8:", name));
            MessageUtils.message(user, String.format("  &7Sound&8:&6 %s", announcement.hasSound() ? announcement.getSound().name() : "NONE"));
            MessageUtils.message(user, "  &7Messages&8:");

            for (String message : announcement.getMessage()) {
                MessageUtils.message(user, String.format("    %s", message));
            }
        }
    }

    @Override
    public String getPermission() {
        return "revelation.announcements.list";
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender user, String[] args) {
        return new ArrayList<>();
    }
}
