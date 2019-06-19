package net.savagedev.ra.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.savagedev.ra.bungee.announcement.AnnouncementManager;
import net.savagedev.ra.bungee.commands.RevelationAnnouncementsCmd;
import net.savagedev.ra.bungee.messaging.MessageManager;
import net.savagedev.ra.bungee.threads.AnnouncementThread;
import net.savagedev.ra.bungee.utils.io.FileUtils;
import net.savagedev.ra.common.Sound;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RABungee extends Plugin {
    private AnnouncementManager announcementManager;
    private AnnouncementThread announcementThread;
    private MessageManager messageManager;
    private Configuration announcements;
    private List<String> sounds;

    @Override
    public void onEnable() {
        this.loadConfig();
        this.loadUtils();
        this.loadCommands();
    }

    @Override
    public void onDisable() {
        this.announcementThread.cancel();
        this.messageManager.close();
    }

    public void reload() {
        this.reloadAnnouncementsFile();
        this.announcementManager.reload();
    }

    private void loadUtils() {
        this.announcementThread = new AnnouncementThread(this, this.announcements.getInt("announcement-delay"));
        this.announcementManager = new AnnouncementManager(this);
        this.messageManager = new MessageManager(this);
        this.sounds = new ArrayList<>();

        for (Sound sound : Sound.values()) {
            sounds.add(sound.name());
        }

        this.announcementThread.start();
    }

    private void loadConfig() {
        File file = new File(this.getDataFolder(), "announcements.yml");
        FileUtils.create("announcements.yml", file);
        this.announcements = FileUtils.load(file);
    }

    private void loadCommands() {
        this.getProxy().getPluginManager().registerCommand(this, new RevelationAnnouncementsCmd(this, "revelationannouncements", "revelation.announcements.use", "ra"));
    }

    public void reloadAnnouncementsFile() {
        this.announcements = FileUtils.load(new File(this.getDataFolder(), "announcements.yml"));
    }

    public boolean isNumber(String potentialNumber) {
        try {
            Integer.parseInt(potentialNumber);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public boolean isValidSound(String name) {
        if (name.equalsIgnoreCase("none")) {
            return true;
        }

        try {
            Sound.valueOf(name);
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    public AnnouncementManager getAnnouncementManager() {
        return this.announcementManager;
    }

    public AnnouncementThread getAnnouncementThread() {
        return this.announcementThread;
    }

    public MessageManager getMessageManager() {
        return this.messageManager;
    }

    public Configuration getAnnouncements() {
        return this.announcements;
    }

    public List<String> getSounds() {
        return this.sounds;
    }
}
