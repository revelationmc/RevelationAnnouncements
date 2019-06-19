package net.savagedev.ra.bungee.announcement;

import net.md_5.bungee.config.Configuration;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.bungee.utils.io.FileUtils;
import net.savagedev.ra.common.Announcement;
import net.savagedev.ra.common.Sound;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class AnnouncementManager {
    private final Map<String, Announcement> announcements;
    private final RABungee plugin;

    public AnnouncementManager(RABungee plugin) {
        this.announcements = new HashMap<>();
        this.plugin = plugin;
        this.init();
    }

    private void init() {
        Configuration announcements = this.plugin.getAnnouncements();

        for (String announcement : announcements.getSection("announcements").getKeys()) {
            this.load(announcements, announcement);
        }
    }

    public void reload() {
        this.announcements.clear();
        this.init();
    }

    public void create(String name, List<String> message, Sound sound) {
        Configuration announcements = this.plugin.getAnnouncements();

        announcements.set(String.format("announcements.%s.sound", name), sound.name());
        announcements.set(String.format("announcements.%s.message", name), message);

        FileUtils.save(announcements, new File(this.plugin.getDataFolder(), "announcements.yml"));
        this.plugin.reloadAnnouncementsFile();

        this.announcements.putIfAbsent(name, new Announcement(message, sound));
    }

    public void edit(String name, int line, String message) {
        Announcement announcement = this.get(name);
        announcement.getMessage().set(line, message);

        Configuration announcements = this.plugin.getAnnouncements();

        announcements.set(String.format("announcements.%s.message", name), announcement.getMessage());

        FileUtils.save(announcements, new File(this.plugin.getDataFolder(), "announcements.yml"));
        this.plugin.reloadAnnouncementsFile();
    }

    public void edit(String name, Sound sound) {
        Announcement announcement = this.get(name);
        announcement.setSound(sound);

        Configuration announcements = this.plugin.getAnnouncements();

        announcements.set(String.format("announcements.%s.sound", name), sound.name());

        FileUtils.save(announcements, new File(this.plugin.getDataFolder(), "announcements.yml"));
        this.plugin.reloadAnnouncementsFile();
    }

    private void load(Configuration announcements, String name) {
        List<String> message = announcements.getStringList(String.format("announcements.%s.message", name));
        String soundName = announcements.getString(String.format("announcements.%s.sound", name));

        if (soundName.equalsIgnoreCase("none")) {
            this.announcements.putIfAbsent(name, new Announcement(message, null));
            return;
        }

        this.announcements.putIfAbsent(name, new Announcement(message, Sound.valueOf(soundName.toUpperCase())));
    }

    public void remove(String name) {
        Configuration announcements = this.plugin.getAnnouncements();

        announcements.set(String.format("announcements.%s", name), null);

        FileUtils.save(announcements, new File(this.plugin.getDataFolder(), "announcements.yml"));
        this.plugin.reloadAnnouncementsFile();

        this.announcements.remove(name);
    }

    public boolean exists(String name) {
        return this.announcements.containsKey(name);
    }

    public Announcement getRandom() {
        List<Announcement> announcements = new ArrayList<>(this.announcements.values());
        return announcements.get(ThreadLocalRandom.current().nextInt(announcements.size()));
    }

    public Announcement get(String name) {
        return this.announcements.get(name);
    }

    public Map<String, Announcement> getAll() {
        return this.announcements;
    }
}
