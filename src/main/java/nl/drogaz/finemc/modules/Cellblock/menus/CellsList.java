package nl.drogaz.finemc.modules.Cellblock.menus;

import nl.drogaz.finemc.framework.chat.ChatUtils;
import nl.drogaz.finemc.framework.gui.PaginatedGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class CellsList extends PaginatedGUI {
    public CellsList(Player player) {
        super(ChatUtils.format("&7Alle Cellen"), 27, player);
    }

    @Override
    protected ItemStack[] getContent() {

        for (int i = 0; i < 27; i++) {
            ItemStack item = new ItemStack(Material.STONE);

        }

        return content;
    }

    @Override
    protected void handleClickAction(InventoryClickEvent event) {

    }

    @Override
    protected void handleCloseAction(InventoryCloseEvent event) {

    }
}
