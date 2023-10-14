package nl.drogaz.finemc.modules.Grinding;

import nl.drogaz.finemc.framework.database.DB;
import nl.drogaz.finemc.framework.modules.AbstractModule;
import nl.drogaz.finemc.modules.Grinding.listeners.Washing;

public class GrindingModule extends AbstractModule {

    private final DB database;

    public GrindingModule(DB database) {
        this.database = database;
    }

    @Override
    public String getName() {
        return "grinding";
    }

    @Override
    public void onEnable() {
        super.onEnable();

         plugin.getServer().getPluginManager().registerEvents(new Washing(this), plugin);
    }


}
