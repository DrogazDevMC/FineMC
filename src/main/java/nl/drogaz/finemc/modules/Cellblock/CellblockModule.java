package nl.drogaz.finemc.modules.Cellblock;

import nl.drogaz.finemc.framework.database.DB;
import nl.drogaz.finemc.framework.modules.AbstractModule;
import nl.drogaz.finemc.modules.Cellblock.commands.Cells;

public class CellblockModule extends AbstractModule {
    private final DB database;

    @Override
    public String getName() {
        return "cellblock";
    }

    public CellblockModule(DB database) {
        this.database = database;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        plugin.getCommandManager().registerCommand(new Cells(database));
    }
}
