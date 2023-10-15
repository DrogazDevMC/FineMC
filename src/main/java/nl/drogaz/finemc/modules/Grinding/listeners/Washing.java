package nl.drogaz.finemc.modules.Grinding.listeners;

import nl.drogaz.finemc.framework.chat.ChatUtils;
import nl.drogaz.finemc.framework.items.ItemBuilder;
import nl.drogaz.finemc.modules.General.items.grinding.WAI;
import nl.drogaz.finemc.modules.Grinding.GrindingModule;
import nl.drogaz.finemc.modules.Grinding.menus.WashMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Washing implements Listener {

    public Washing(GrindingModule grindingModule) {
    }


    @EventHandler
    public void onWash(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if (!(e.getAction()).equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getHand().equals(EquipmentSlot.HAND)) return;
        if (e.getClickedBlock().getType() == Material.MAGENTA_GLAZED_TERRACOTTA)  {
           new WashMenu(p).open(p);
        }
    }

    @EventHandler
    public void onWashBin(PlayerInteractEvent e) {
        if (!(e.getAction()).equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getHand().equals(EquipmentSlot.HAND)) return;
        if (e.getClickedBlock().getType() == Material.LIME_GLAZED_TERRACOTTA) {

            Player p = e.getPlayer();
            p.getInventory().addItem(WAI.dc(new Random().nextInt(4) + 1));
            ChatUtils.send(p, "&7Je hebt smerige kleren uit de wasmand gepakt!");

        }
    }
}
