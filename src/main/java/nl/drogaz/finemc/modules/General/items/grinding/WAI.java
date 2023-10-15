package nl.drogaz.finemc.modules.General.items.grinding;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.drogaz.finemc.framework.chat.ChatUtils;
import nl.drogaz.finemc.framework.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WAI {
    public static ItemStack dc_I() {
        ItemStack dc_I = new ItemBuilder(Material.LEATHER)
                .setName(ChatUtils.format("&fSmerige Kleren"))
                .toItemStack();
        dc_I = NBTEditor.set(dc_I, "dirty_clothes", "finemc");
        return dc_I;
    }

    public static ItemStack dc(Integer amount) {
        ItemStack dc_I = new ItemBuilder(Material.LEATHER)
                .setName(ChatUtils.format("&fSmerige Kleren"))
                .toItemStack();
        dc_I = NBTEditor.set(dc_I, "dirty_clothes", "finemc");
        return dc_I;
    }

    public static ItemStack cc(Integer amount) {
        ItemStack cc = new ItemBuilder(Material.LEATHER)
                .setName(ChatUtils.format("&fSchone Kleren"))
                .toItemStack();
        cc = NBTEditor.set(cc, "clean_clothes", "finemc");
        return cc;
    }
}
