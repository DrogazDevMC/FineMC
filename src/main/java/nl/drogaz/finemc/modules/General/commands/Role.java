package nl.drogaz.finemc.modules.General.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import nl.drogaz.finemc.framework.database.DB;
import nl.drogaz.finemc.modules.General.GeneralModule;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@CommandAlias("setrole|role")
@CommandPermission("finemc.staff")
public class Role extends BaseCommand {

    private final DB database;

    public Role(DB database, GeneralModule generalModule) {
        this.database = database;
    }

    //    parameters Player, Role
    @Default
    @CommandCompletion("@players Guard|Prisoner|Warden")
    public void onCommand(Player p, String[] args) throws SQLException {
        if (args.length != 2) return;

        Player target = p.getServer().getPlayer(args[0]);
        String role = args[1];

        if (target == null) {
            p.sendMessage("Player not found!");
            return;
        }

        if (!(role.equals("Prisoner") || role.equals("Guard") || role.equals("Warden"))) {
            p.sendMessage("Role not found!");
            return;
        }

        database.setData(target, "rank", role);
        p.sendMessage("Set " + target.getName() + "'s role to " + role);
    }
}
