package net.savagedev.ra.bungee.commands.subcommands;

import net.md_5.bungee.api.CommandSender;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.bungee.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class DelayCmd implements SubCommand {
    private final RABungee plugin;

    public DelayCmd(RABungee plugin) {
        this.plugin = plugin;
    }

    @Override // delay [delay]
    public void execute(CommandSender user, String... args) {
        if (args.length == 1) {
            MessageUtils.message(user, String.format("&aThe current delay is %d minutes.", this.plugin.getAnnouncementThread().getDelay()));
            return;
        }

        String delayStr = args[1];

        if (!this.plugin.isNumber(delayStr)) {
            MessageUtils.message(user, "&cThe delay must be a number! (In minutes)");
            return;
        }

        int delay = Integer.valueOf(delayStr);

        this.plugin.getAnnouncementThread().setDelay(delay);
        MessageUtils.message(user, String.format("&aDelay updated to %d minutes.", delay));
    }

    @Override
    public String getPermission() {
        return "revelation.announcements.delay";
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender user, String[] args) {
        if (args.length == 2) {
            List<String> numbers = new ArrayList<>();
            String delayStr = args[1];

            if (!this.plugin.isNumber(delayStr)) {
                for (int i = 1; i < 10; i++) {
                    numbers.add(String.valueOf(i));
                }

                return numbers;
            }

            int delay = Integer.valueOf(delayStr);

            for (int i = delay; i < delay + 10; i++) {
                numbers.add(String.valueOf(i));
            }

            return numbers;
        }

        return new ArrayList<>();
    }
}
