package nl.drogaz.finemc.modules.General.listeners;

import nl.drogaz.finemc.framework.chat.ChatUtils;
import nl.drogaz.finemc.modules.General.GeneralModule;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Combat implements Listener {

    public Combat(GeneralModule generalModule) {
    }

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            Player t = (Player) e.getEntity();
            if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
                if (Math.random() < 0.01) {
                    p.sendMessage(ChatUtils.format("&aJe hebt " + t.getName() + " een box gegeven!"));
                    t.sendMessage(ChatUtils.format("&aJe hebt een box gekregen van " + p.getName() + "!"));
                } else {
                    e.setCancelled(true);
                    p.sendMessage(ChatUtils.format("&cJe kunt niet met je vuisten slaan!"));
                }
            }
        }


    }

}
