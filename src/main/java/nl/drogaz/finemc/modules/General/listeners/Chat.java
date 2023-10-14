package nl.drogaz.finemc.modules.General.listeners;

import nl.drogaz.finemc.framework.chat.ChatUtils;
import nl.drogaz.finemc.framework.database.DB;
import nl.drogaz.finemc.modules.General.GeneralModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.sql.SQLException;

public class Chat implements Listener {

    private final DB database;
    private final GeneralModule generalModule;
    public Chat(DB database, GeneralModule generalModule) {
        this.database = database;
        this.generalModule = generalModule;
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) throws SQLException {
        Player p = e.getPlayer();

        e.setFormat(ChatUtils.format("&8[&bTier " + database.getData(p, "tier") + "&8] &8[&7" + database.getData(p, "rank") + "&8] &7" + e.getPlayer().getName() + " &8» &f" + e.getMessage()));
    }

}