package uk.co.kieraan.mymessages.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.kieraan.mymessages.MasterCommand;
import uk.co.kieraan.mymessages.MyMessages;

public class SetMotdCommand extends MasterCommand {

    MyMessages plugin;

    public SetMotdCommand(MyMessages plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        String newMotd = "";

        if (!isPlayer) {
            sender.sendMessage(ChatColor.RED + "Run this command from ingame.");
            return;
        }

        if (!player.hasPermission("mymessages.setmotd")) {
            player.sendMessage(ChatColor.RED + "Access denied.");
            return;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /" + commandName + " <message>");
            return;
        }

        newMotd += this.plugin.combineSplit(0, args, " ");
        this.plugin.getConfig().set("server.motd", newMotd);
        this.plugin.saveConfig();
        for (Player plr : this.plugin.getServer().getOnlinePlayers()) {
            if (plr.hasPermission("mymessages.set.motd") && !plr.equals(player)) {
                plr.sendMessage(ChatColor.GOLD + player.getDisplayName() + ChatColor.GOLD + " changed the server MOTD.");
            }
        }
        player.sendMessage(ChatColor.GOLD + "MOTD set to: \"" + ChatColor.RESET + this.plugin.format(newMotd) + ChatColor.GOLD + "\"");
    }

}
