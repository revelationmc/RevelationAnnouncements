package net.savagedev.ra.bungee.commands.subcommands;

import net.md_5.bungee.api.CommandSender;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.bungee.utils.MessageUtils;
import net.savagedev.ra.common.Announcement;
import net.savagedev.ra.common.Sound;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class EditCmd implements SubCommand {
    private final List<String> editTypes;
    private final RABungee plugin;

    public EditCmd(RABungee plugin) {
        this.editTypes = new ArrayList<>();
        this.plugin = plugin;
        this.init();
    }

    private void init() {
        this.editTypes.add("message");
        this.editTypes.add("sound");
    }

    @Override // edit <name> <message/sound> <[line]/sound> <message>
    public void execute(CommandSender user, String... args) {
        if (args.length == 1) {
            MessageUtils.message(user, "&cInvalid arguments! Try: /ra edit <name> <message/sound> <[line]/sound> <message>");
            return;
        }

        String name = args[1].toLowerCase();

        if (!this.plugin.getAnnouncementManager().exists(name)) {
            MessageUtils.message(user, String.format("&cAn announcement by the name of %s does not exist.", name));
            return;
        }

        Announcement announcement = this.plugin.getAnnouncementManager().get(name);

        if (args.length == 2) {
            MessageUtils.message(user, String.format("&cInvalid arguments! Try: /ra edit %s <message/sound> <[line]/sound> <message>", name));
            return;
        }

        String editTypeName = args[2].toUpperCase();

        if (!this.isValidEditType(editTypeName)) {
            MessageUtils.message(user, "&cInvalid edit type! Try \"message\" or \"sound\"");
            return;
        }

        EditType editType = EditType.valueOf(editTypeName);

        if (editType == EditType.MESSAGE) {
            if (args.length == 3) {
                MessageUtils.message(user, String.format("&cInvalid arguments! Try: /ra edit %s %s <line> <message>", name, "message"));
                return;
            }

            String lineStr = args[3];

            if (!this.plugin.isNumber(lineStr)) {
                MessageUtils.message(user, "&cThe line must be a number!");
                return;
            }

            int line = Integer.valueOf(lineStr) - 1;

            if (line < 0 || line > announcement.getMessage().size() - 1) {
                MessageUtils.message(user, "&cInvalid line number!");
                return;
            }

            StringBuilder message = new StringBuilder();

            for (int i = 4; i < args.length; i++) {
                message.append(args[i]).append(" ");
            }

            this.plugin.getAnnouncementManager().edit(name, line, message.toString().trim());
            MessageUtils.message(user, "&aAnnouncement message updated.");
            return;
        }

        if (editType == EditType.SOUND) {
            if (args.length == 3) {
                MessageUtils.message(user, String.format("&cInvalid arguments! Try: /ra edit %s %s <sound>", name, "sound"));
                return;
            }

            String soundName = args[3].toUpperCase();

            if (!this.plugin.isValidSound(soundName)) {
                MessageUtils.message(user, "&cInvalid sound!");
                return;
            }

            this.plugin.getAnnouncementManager().edit(name, Sound.valueOf(soundName));
            MessageUtils.message(user, "&aAnnouncement sound updated.");
        }
    }

    @Override
    public String getPermission() {
        return "revelation.announcements.edit";
    }

    private boolean isValidEditType(String name) {
        try {
            EditType.valueOf(name);
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
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

        if (args.length == 3) {
            String name = args[2].toLowerCase();

            List<String> suggestions = new ArrayList<>();

            for (String editType : this.editTypes) {
                if (editType.startsWith(name)) {
                    suggestions.add(editType);
                }
            }

            return suggestions;
        }

        if (args.length >= 3) {
            String editTypeName = args[2].toUpperCase();

            if (!this.isValidEditType(editTypeName)) {
                return new ArrayList<>();
            }

            EditType editType = EditType.valueOf(editTypeName);

            if (editType == EditType.MESSAGE) {
                Announcement announcement = this.plugin.getAnnouncementManager().get(args[1].toLowerCase());

                if (args.length == 4) {
                    String name = args[3].toLowerCase();

                    List<String> suggestions = new ArrayList<>();

                    if (!this.plugin.isNumber(name)) {
                        for (int i = 1; i <= announcement.getMessage().size(); i++) {
                            suggestions.add(String.valueOf(i));
                        }

                        return suggestions;
                    }

                    int line = Integer.valueOf(name);

                    for (int i = line; i <= announcement.getMessage().size(); i++) {
                        suggestions.add(String.valueOf(i));
                    }

                    return suggestions;
                }
            }

            if (editType == EditType.SOUND) {
                if (args.length == 4) {
                    String name = args[3].toLowerCase();

                    List<String> suggestions = new ArrayList<>();

                    for (String sound : this.plugin.getSounds()) {
                        if (sound.contains(name.toUpperCase())) {
                            suggestions.add(sound);
                        }
                    }

                    return suggestions;
                }
            }
        }

        return new ArrayList<>();
    }

    private enum EditType {
        MESSAGE,
        SOUND
    }
}
