package nl.drogaz.finemc.modules.Cellblock.menus.buttons;

import nl.drogaz.finemc.framework.items.ItemBuilder;
import nl.fenixnetwerk.modules.menu.button.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CellButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.IRON_DOOR).setName("Test").toItemStack();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        player.sendMessage("clicked");
    }
}
