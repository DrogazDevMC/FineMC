package nl.drogaz.finemc.modules.Cellblock.menus;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Maps;
import nl.drogaz.finemc.Main;
import nl.drogaz.finemc.framework.database.DB;
import nl.drogaz.finemc.modules.Cellblock.menus.buttons.CellButton;
import nl.fenixnetwerk.modules.menu.button.Button;
import nl.fenixnetwerk.modules.menu.pagination.PaginatedMenu;
import nl.fenixnetwerk.modules.utils.ItemBuilder;
import org.bukkit.entity.Player;
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
        HashMap<Integer, Button> buttons = Maps.newHashMap();

        try {
            List<Map<String, Object>> cells = Main.getInstance().getDatabase().getAllData("cells");

            for (int i = 0; i < cells.size(); i++) {
                buttons.put(i, new CellButton());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buttons.put(2, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(XMaterial.STICK.parseMaterial()).build();
            }
        });

        return buttons;
    }
}
