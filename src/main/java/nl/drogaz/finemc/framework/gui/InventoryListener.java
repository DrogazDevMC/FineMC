package nl.drogaz.finemc.framework.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;

        InventoryHolder inventoryHolder = inventory.getHolder();
        if (!(inventoryHolder instanceof GUI)) return;
        GUI gui = (GUI) inventoryHolder;

        event.setCancelled(true);
        gui.handleClickAction(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        InventoryHolder inventoryHolder = inventory.getHolder();
        if (!(inventoryHolder instanceof GUI)) return;
        GUI gui = (GUI) inventoryHolder;

        gui.handleCloseAction(event);
    }
}
