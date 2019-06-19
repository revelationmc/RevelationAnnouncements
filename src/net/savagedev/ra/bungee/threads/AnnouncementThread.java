package net.savagedev.ra.bungee.threads;

import net.md_5.bungee.config.Configuration;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.bungee.utils.io.FileUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AnnouncementThread extends Thread {
    private final RABungee plugin;
    private boolean running;
    private int delay;

    public AnnouncementThread(RABungee plugin, int delay) {
        this.plugin = plugin;
        this.running = true;
        this.delay = delay;
    }

    @Override
    public void run() {
        System.out.println("Announcement thread started.");

        do {
            this.plugin.getMessageManager().send(this.plugin.getAnnouncementManager().getRandom());

            try {
                Thread.sleep(TimeUnit.MINUTES.toMillis(this.delay));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (this.running);

        System.out.println("Announcement thread stopped.");
    }

    public void setDelay(int delay) {
        Configuration announcements = this.plugin.getAnnouncements();
        announcements.set("announcement-delay", delay);
        FileUtils.save(announcements, new File(this.plugin.getDataFolder(), "announcements.yml"));

        this.delay = delay;
    }

    public void cancel() {
        this.running = false;
    }

    public int getDelay() {
        return this.delay;
    }
}
