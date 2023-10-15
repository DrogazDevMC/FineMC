package nl.drogaz.finemc.modules.General.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import nl.drogaz.finemc.framework.chat.ChatUtils;

import static org.bukkit.Bukkit.getServer;

@CommandAlias("broadcast|bc")
@CommandPermission("finemc.staff")
public class Broadcast extends BaseCommand {

    @Default
    public void onCommand(String[] args) {
        if (args.length == 0) return;

        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }

        getServer().broadcastMessage(ChatUtils.format("&r彙 &8> &7") + message);
    }

}
