package net.savagedev.ra.spigot;

import net.savagedev.ra.spigot.messaging.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RASpigot extends JavaPlugin {
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        this.loadUtils();
    }

    @Override
    public void onDisable() {
        this.messageManager.close();
    }

    private void loadUtils() {
        this.messageManager = new MessageManager(this);
    }
}
