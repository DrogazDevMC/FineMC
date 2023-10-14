package nl.drogaz.finemc.modules.Grinding.menus;

import nl.drogaz.finemc.framework.chat.ChatUtils;
import nl.drogaz.finemc.framework.gui.GUI;
import nl.drogaz.finemc.framework.items.ItemBuilder;
import nl.drogaz.finemc.framework.items.ItemRemover;
import nl.drogaz.finemc.framework.items.NBTEditor;
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
//        for (int i = 0; i < 27; i++) {
//            ItemStack leeg = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName(" ").toItemStack();
//            inventory.setItem(i, leeg);
//        }

        ItemStack washerClick = new ItemBuilder(Material.WATER_BUCKET).setName(ChatUtils.format("&fKlik hier om je kleren te wassen!")).toItemStack();
        inventory.setItem(13, washerClick);
    }

    @Override
    protected void handleClickAction(InventoryClickEvent e) {
        e.setCancelled(true);
////        e.setCancelled(true);
//        if (e.getSlot() == 13) {
//            ItemStack dirtyClothes = new ItemBuilder(Material.LEATHER).setName(ChatUtils.format("&fSmerige Kleren")).toItemStack();
//            dirtyClothes  = NBTEditor.set(dirtyClothes, "dirty_clothes", "FineMC-PRISON");
//
//            if (player.getInventory().contains(dirtyClothes)) {
////               remove one dirty clothes
//                ItemRemover.removeNamedItems(player.getInventory(), Material.LEATHER, 1, "dirty_clothes", "FineMC-PRISON");
//                ItemStack cleanClothes = new ItemBuilder(Material.LEATHER).setName(ChatUtils.format("&fSchone Kleren")).toItemStack();
//                cleanClothes  = NBTEditor.set(dirtyClothes, "clean_clothes", "FineMC-PRISON");
//                player.getInventory().addItem(cleanClothes);
//                player.sendMessage(ChatUtils.format("&fJe kleren zijn nu schoon!"));
//            } else {
//                player.sendMessage(ChatUtils.format("&fJe hebt geen smerige kleren!"));
//            }
//        }
    }

    @Override
    protected void handleCloseAction(InventoryCloseEvent event) {

    }
}
