package nl.drogaz.finemc.modules.General;

import nl.drogaz.finemc.framework.database.DB;
import nl.drogaz.finemc.framework.gui.InventoryListener;
import nl.drogaz.finemc.framework.modules.AbstractModule;
import nl.drogaz.finemc.modules.General.commands.Broadcast;
import nl.drogaz.finemc.modules.General.commands.Role;
import nl.drogaz.finemc.modules.General.commands.Tier;
import nl.drogaz.finemc.modules.General.listeners.Chat;
import nl.drogaz.finemc.modules.General.listeners.Combat;
import nl.drogaz.finemc.modules.General.listeners.FirstJoin;
import nl.fenixnetwerk.modules.menu.MenuListener;
import org.bukkit.Bukkit;

public class GeneralModule extends AbstractModule {

    private final DB database;

    public GeneralModule(DB database) {
        this.database = database;
    }

    @Override
    public String getName() {
        return "general";
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // Utils
        plugin.getServer().getPluginManager().registerEvents(new InventoryListener(), plugin);

        //General
        plugin.getServer().getPluginManager().registerEvents(new Chat(database, this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new FirstJoin(database, this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new Combat(this), plugin);

        //Commands
        plugin.getCommandManager().registerCommand(new Role(database, this));
        plugin.getCommandManager().registerCommand(new Tier(database, this));
        plugin.getCommandManager().registerCommand(new Broadcast());
    }
}
