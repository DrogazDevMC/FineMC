package nl.drogaz.finemc.modules.General.listeners;

import nl.drogaz.finemc.framework.database.DB;
import nl.drogaz.finemc.modules.General.GeneralModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class FirstJoin implements Listener {

    private final DB database;
    private final GeneralModule generalModule;

    public FirstJoin(DB database, GeneralModule generalModule) {
        this.database = database;
        this.generalModule = generalModule;
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        Boolean playerExists = database.playerExists(p);

        if (!playerExists) {
            database.addPlayer(p);
            p.sendMessage("Welcome to the server!");
        }
    }
}
