package nl.drogaz.finemc.framework.modules;

import nl.drogaz.finemc.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class AbstractModule implements Listener {

    protected final Main plugin = Main.getInstance();
    public abstract String getName();

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public Main getPlugin() {
        return this.plugin;
    }

}