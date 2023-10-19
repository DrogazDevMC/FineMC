package nl.drogaz.finemc;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import nl.drogaz.finemc.framework.database.DB;
import nl.drogaz.finemc.framework.modules.AbstractModule;
import nl.drogaz.finemc.modules.Cellblock.CellblockModule;
import nl.drogaz.finemc.modules.General.GeneralModule;
import nl.drogaz.finemc.modules.Grinding.GrindingModule;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class Main extends JavaPlugin {

    @Getter private static Main instance;
    @Getter private PaperCommandManager commandManager;
    private DB database;

    private final List<AbstractModule> modules = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        this.commandManager = new PaperCommandManager(this);
        database = new DB(this);

        this.initiateModules(
                new GeneralModule(database),
                new GrindingModule(database),
                new CellblockModule(database)
        );

        this.modules.forEach(AbstractModule::onEnable);
    }

    @Override
    public void onDisable() {
        this.modules.forEach(AbstractModule::onEnable);

        database.closeConnection();
    }

    private Optional<AbstractModule> findModule(String name) {
        return this.modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findAny();
    }

    private void initiateModules(AbstractModule... modules) {
        this.modules.addAll(Arrays.asList(modules));
    }
}