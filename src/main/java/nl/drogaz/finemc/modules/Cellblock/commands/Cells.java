package nl.drogaz.finemc.modules.Cellblock.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.Vector2;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.drogaz.finemc.framework.chat.ChatResponse;
import nl.drogaz.finemc.framework.chat.ChatUtils;
import nl.drogaz.finemc.framework.database.DB;
import nl.drogaz.finemc.modules.Cellblock.menus.CellsList;
import nl.drogaz.finemc.modules.General.GeneralModule;
import nl.drogaz.finemc.modules.Grinding.menus.WashMenu;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@CommandAlias("cells")
@CommandPermission("finemc.admin")
public class Cells extends BaseCommand {

    private final DB database;

    public Cells(DB database) {
        this.database = database;
    }

    @Default
    public void onCommand(CommandSender s) {
        s.sendMessage(ChatUtils.format("&8&l&m--------------------------------------------"));
        s.sendMessage(ChatUtils.format("&c/cells &8- &7Dit commando."));
        s.sendMessage(ChatUtils.format("&c/cells new &8- &7Maak een cell"));
//        s.sendMessage(ChatUtils.format("&c/cells plaatsnpc &a<&2gang-naam&a> <&2skin&a> &8- &7Plaats NPC voor gang management."));
//        s.sendMessage(ChatUtils.format("&c/cells addmember &a<&2gang-naam&a> <&2online-speler&a> &8- &7Te gebruiken door gang owners met (gangs.owner)."));
//        s.sendMessage(ChatUtils.format("&c/cells addmemberstaff &a<&2gang-naam&a> <&2online-speler&a> &8- &7Gebruik dit als staff."));
//        s.sendMessage(ChatUtils.format("&c/cells kick &a<&2gang-naam&a> <&2online-speler&a> &8- &7Te gebruiken door gang owners met (gangs.owner)."));
        s.sendMessage(ChatUtils.format("&c/cells list &8- &7Open een UI met alle cellen."));
//        s.sendMessage(ChatUtils.format("&c/cells remove &a<&2gang-naam&a> &8- &7Verwijder een gang."));
        s.sendMessage(ChatUtils.format(""));
        s.sendMessage(ChatUtils.format("&cEigendom van FineMC - v.1.4"));
        s.sendMessage(ChatUtils.format("&cDevelopers: &a83J, iTouchinq"));
        s.sendMessage(ChatUtils.format("&8&l&m--------------------------------------------"));
    }

    @Subcommand("new")
    public void onNewCommand(CommandSender s) {

        Player p = (Player) s;

        ApplicableRegionSet regionSet = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(p.getWorld())).getApplicableRegions(BukkitAdapter.asBlockVector(p.getLocation()));

        boolean inCell = false; // Initialize the flag to false
        String cellName = "";

        for (ProtectedRegion region : regionSet) {
            if (region.getId().startsWith("cell-")) {
                inCell = true; // The player is in a cell
                cellName = region.getId();
                break; // Exit the loop since we already found a cell
            }
        }

        if (inCell) {
            String finalCellName = cellName;
            new ChatResponse(p, ChatUtils.format("&r彙 &8> &7Type &a&lBUITEN &7om de buitenkant van de cell aan te geven"), (i) -> {
                if (i.equalsIgnoreCase("buiten")) {
                    Location outside = p.getLocation();
                    new ChatResponse(p, ChatUtils.format("&r彙 &8> &7Type &a&lBINNEN &7om de binnen van de cell aan te geven"), (o) -> {
                        if (o.equalsIgnoreCase("binnen")) {
                            Location inside = p.getLocation();
                            new ChatResponse(p, ChatUtils.format("&r彙 &8> &7Type een &aprijs &7in voor deze cell"), (price) -> {
                                if (!(price.isEmpty())) {
                                    try {
                                        database.addCell(p.getUniqueId(), finalCellName, Integer.parseInt(price), inside, outside);
                                        p.sendMessage(ChatUtils.format("&r彙 &8> &7Je hebt een cell aangemaakt met de naam &a" + finalCellName + "&7."));
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        } else {
                            p.sendMessage(ChatUtils.format("&r彙 &8> &7Je hebt een ongeldige waarde ingevoerd, probeer het opnieuw."));
                        }
                    });
                } else {
                    p.sendMessage(ChatUtils.format("&r彙 &8> &7Je hebt een ongeldige waarde ingevoerd, probeer het opnieuw."));
                }
            });
        } else {
            p.sendMessage(ChatUtils.format("&r彙 &8> &7Je staat niet in een cel!"));
        }
    }

    @Subcommand("list")
    public void onListCommand(CommandSender s) {

        Player p = (Player) s;

        new CellsList(p).open();

//        List<Map<String, Object>> cells = database.getAllData("cells");
//
//        p.sendMessage(ChatUtils.format("&r彙 &8> &7Alle cells:"));
//        for (Map<String, Object> cell : cells) {
//            p.sendMessage(ChatUtils.format("&r彙 &8> &7Cell: &a" + cell.get("name") + "&7, prijs: &a" + cell.get("price") + "&7, owner: &a" + ChatUtils.UUIDtoName(cell.get("uuid").toString())));
//        }

    }
}
