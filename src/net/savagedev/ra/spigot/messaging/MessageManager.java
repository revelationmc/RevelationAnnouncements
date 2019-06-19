package net.savagedev.ra.spigot.messaging;

import net.savagedev.ra.common.Announcement;
import net.savagedev.ra.common.DataStreamUtils;
import net.savagedev.ra.spigot.RASpigot;
import net.savagedev.ra.spigot.utils.MessageUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageManager implements PluginMessageListener {
    private static final String CHANNEL = "ra:announcements";
    private final RASpigot plugin;

    public MessageManager(RASpigot plugin) {
        this.plugin = plugin;
        this.init();
    }

    private void init() {
        this.plugin.getServer().getMessenger().registerIncomingPluginChannel(this.plugin, CHANNEL, this);
        this.plugin.getServer().getMessenger().registerOutgoingPluginChannel(this.plugin, CHANNEL);
    }

    public void close() {
        this.plugin.getServer().getMessenger().unregisterIncomingPluginChannel(this.plugin, CHANNEL);
        this.plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(this.plugin, CHANNEL);
    }

    @Override
    public void onPluginMessageReceived(@Nonnull String channel, @Nonnull Player user, @Nonnull byte[] message) {
        if (!channel.equals(CHANNEL)) {
            return;
        }

        ObjectInputStream inputStream = DataStreamUtils.newInputStream(message);

        if (inputStream == null) {
            return;
        }

        try {
            Object input = inputStream.readObject();

            if (input instanceof Announcement) {
                Announcement announcement = (Announcement) input;

                if (announcement.hasSound()) {
                    MessageUtils.broadcast(announcement.getMessage(), Sound.valueOf(announcement.getSound().name()));
                    return;
                }

                MessageUtils.broadcast(announcement.getMessage());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
