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

@CommandAlias("tier|settier")
@CommandPermission("finemc.staff")
public class Tier extends BaseCommand {

    private final DB database;

    public Tier(DB database, GeneralModule generalModule) {
        this.database = database;
    }

    @Default
    @CommandCompletion("@players 俰|俱|俲|俳|俴")
    public void onCommand(Player p, String[] args) throws SQLException {
        if (args.length != 2) return;

        Player target = p.getServer().getPlayer(args[0]);
        String tier = args[1];

        if (target == null) {
            p.sendMessage("Player not found!");
            return;
        }

        if (!(tier.equals("俰") || tier.equals("俱") || tier.equals("俲") || tier.equals("俳") || tier.equals("俴"))) {
            p.sendMessage("Tier not found!");
            return;
        }

        database.setData(target, "tier", tier);
        p.sendMessage("Set " + target.getName() + "'s tier to " + tier);
    }
}
