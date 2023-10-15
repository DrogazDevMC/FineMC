package nl.drogaz.finemc.modules.General.listeners;

import nl.drogaz.finemc.framework.chat.ChatUtils;
import nl.drogaz.finemc.framework.database.DB;
import nl.drogaz.finemc.modules.General.GeneralModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.SQLException;

public class Chat implements Listener {

    private final DB database;
    private final GeneralModule generalModule;
    public Chat(DB database, GeneralModule generalModule) {
        this.database = database;
        this.generalModule = generalModule;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) throws SQLException {
        Player p = e.getPlayer();
        String tier = database.getData(p, "tier");
        String rank = rankColor(database.getData(p, "rank"));

        e.setFormat(ChatUtils.format(tier + " &8| " + rank + " &7" + e.getPlayer().getName() + "&7: &7") + e.getMessage());
    }

    public String rankColor(String rank) {
        if (rank.equals("Warden")) {
            rank = "#ed3300ᴡᴀʀᴅᴇɴ";
        } if (rank.equals("Keeper")) {
            rank = "#003bdeᴋᴇᴇᴘᴇʀ";
        } if (rank.equals("Guard")) {
            rank = "#396dfaɢᴜᴀʀᴅ";
        } if (rank.equals("Prisoner")) {
            rank = "#ff6e2bᴘʀɪsᴏɴᴇʀ";
        }
        return rank;
    }

}