package nl.drogaz.finemc.modules.Cellblock.menus;

import com.google.common.collect.Maps;
import nl.drogaz.finemc.Main;
import nl.drogaz.finemc.framework.chat.ChatUtils;
import nl.drogaz.finemc.modules.Cellblock.menus.buttons.CellButton;
import nl.fenixnetwerk.modules.menu.button.Button;
import nl.fenixnetwerk.modules.menu.pagination.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellsList extends PaginatedMenu {
    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Cell Lijst";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {

        try {
            List<Map<String, Object>> cells = Main.getInstance().getDatabase().getAllData("cells");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        HashMap<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(0, new CellButton());

        return buttons;

    }
}
