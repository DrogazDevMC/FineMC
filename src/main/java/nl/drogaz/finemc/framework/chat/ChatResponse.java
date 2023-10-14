package nl.drogaz.finemc.framework.chat;

import nl.drogaz.finemc.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public class ChatResponse extends BukkitRunnable implements Listener {

    private Player player;
    private final Consumer<String> consumer; // returns player's message

    /**
     * Makes a player to make a response in the chat + disables from the player to receive any messages
     *
     * @param player   - the player whom the plugin will wait
     * @param message  - last message the player will see
     * @param consumer - your function, what to do with the message player sent to the chat
     * @param wait     - in seconds, how much time the plugin will wait for the player
     */
    public ChatResponse(Player player, String message, Consumer<String> consumer, int wait) {
        this.player = player;
        player.sendMessage(message);

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
        this.consumer = consumer;
        runTaskLater(Main.getInstance(), wait * 20L);
    }

    /**
     * Makes the default wait time 10 seconds
     */
    public ChatResponse(Player player, String message, Consumer<String> consumer) {
        this(player, message, consumer, 10);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!event.getPlayer().equals(player)) return;

        event.getRecipients().remove(player);
        event.setCancelled(true);

        consumer.accept(event.getMessage());
        player = null;

        HandlerList.unregisterAll(this);
        cancel();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!event.getPlayer().equals(player)) return;

        HandlerList.unregisterAll(this);
        cancel();
    }

    @Override
    public void run() {
        if (this.player != null) {
            this.player.sendMessage(ChatUtils.format("&cDat duurde iets te lang..."));
            HandlerList.unregisterAll(this);
        }
        cancel();
    }
}