package nl.drogaz.finemc.modules.Grinding.menus;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.drogaz.finemc.framework.chat.ChatUtils;
import nl.drogaz.finemc.framework.gui.GUI;
import nl.drogaz.finemc.framework.items.ItemBuilder;
import nl.drogaz.finemc.framework.items.ItemRemover;
import nl.drogaz.finemc.modules.General.items.grinding.WAI;
import nl.drogaz.finemc.modules.Grinding.listeners.Washing;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class WashMenu extends GUI {

    private final Player player;

    public WashMenu(Player player) {
        super(ChatUtils.format("&8Wasmachine - FineMC"), 3);
        this.player = player;
    }


    @Override
    protected void setContents() {
        for (int i = 0; i < 27; i++) {
            ItemStack leeg = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName(" ").toItemStack();
            inventory.setItem(i, leeg);
        }

        ItemStack washerClick = new ItemBuilder(Material.WATER_BUCKET).setName(ChatUtils.format("&fKlik hier om je kleren te wassen!")).toItemStack();
        inventory.setItem(13, washerClick);
    }

    @Override
    protected void handleClickAction(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        e.setCancelled(true);
        if (e.getSlot() == 13) {

            if (player.getInventory().containsAtLeast(WAI.dc_I(), 1)) {
                int count = 0;

                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null && item.isSimilar(WAI.dc_I())) {
                        // Check if the item has an NBT tag for "dirty_clothes"
                        if (NBTEditor.getString(item, "dirty_clothes") != null) {
                            count += item.getAmount();
                        }
                    }
                }

                ItemRemover.removeNamedItems(player.getInventory(), Material.LEATHER, count, "dirty_clothes", "finemc");
                p.closeInventory();
                ChatUtils.send(p, "&7Je hebt &a" + count + " &7smerige kleren gewassen!");
            } else {
                ChatUtils.send(p, "&7Je hebt geen smerige kleren!");
            }

        }
    }

    @Override
    protected void handleCloseAction(InventoryCloseEvent event) {

    }
}
