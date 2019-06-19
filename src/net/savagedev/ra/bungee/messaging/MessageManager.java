package net.savagedev.ra.bungee.messaging;

import net.md_5.bungee.api.config.ServerInfo;
import net.savagedev.ra.bungee.RABungee;
import net.savagedev.ra.common.Announcement;
import net.savagedev.ra.common.DataStreamUtils;

public class MessageManager {
    private static final String CHANNEL = "ra:announcements";
    private final RABungee plugin;

    public MessageManager(RABungee plugin) {
        this.plugin = plugin;
        this.init();
    }

    private void init() {
        this.plugin.getProxy().registerChannel(CHANNEL);
    }

    public void close() {
        this.plugin.getProxy().unregisterChannel(CHANNEL);
    }

    public void send(Announcement announcement) {
        for (ServerInfo server : this.plugin.getProxy().getServers().values()) {
            if (!server.getPlayers().isEmpty()) {
                server.sendData(CHANNEL, DataStreamUtils.toByteArray(announcement));
            }
        }
    }
}
