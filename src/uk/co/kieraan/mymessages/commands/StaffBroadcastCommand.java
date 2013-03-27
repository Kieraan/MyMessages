package uk.co.kieraan.mymessages.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.kieraan.mymessages.MasterCommand;
import uk.co.kieraan.mymessages.MyMessages;

public class StaffBroadcastCommand extends MasterCommand {

    MyMessages plugin;

    public StaffBroadcastCommand(MyMessages plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        String broadcastMessage = "";
        String broadcastStaffTag = "";
        String broadcastRegularTag = "";

        if (!isPlayer) {
            sender.sendMessage(ChatColor.RED + "Run this command from ingame.");
        }

        if (!player.hasPermission("mymessages.broadcast.staff")) {
            player.sendMessage(ChatColor.RED + "Access denied.");
            return;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /" + commandName + " <message>");
            return;
        }

        broadcastMessage += this.plugin.combineSplit(0, args, " ");
        broadcastMessage = this.plugin.formatColors(broadcastMessage);

        broadcastStaffTag += this.plugin.getConfig().getString("broadcast.staff_tag");
        broadcastStaffTag = this.plugin.formatColors(broadcastStaffTag);
        broadcastStaffTag = broadcastStaffTag.replace("<player>", player.getDisplayName());

        broadcastRegularTag += this.plugin.getConfig().getString("broadcast.regular_tag");
        broadcastRegularTag = this.plugin.formatColors(broadcastRegularTag);
        broadcastRegularTag = broadcastRegularTag.replace("<player>", player.getDisplayName());

        if (broadcastMessage != null && broadcastStaffTag != null && broadcastRegularTag != null) {
            for (Player plr : this.plugin.getServer().getOnlinePlayers()) {
                if (plr.hasPermission("mymessages.broadcast.staff")) {
                    plr.sendMessage(broadcastStaffTag + ChatColor.RESET + " " + broadcastMessage);
                } else {
                    plr.sendMessage(broadcastRegularTag + ChatColor.RESET + " " + broadcastMessage);
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "Error: Could not broadcast message.");
        }

    }

}
