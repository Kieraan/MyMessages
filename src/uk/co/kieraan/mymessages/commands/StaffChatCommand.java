package uk.co.kieraan.mymessages.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.kieraan.mymessages.MasterCommand;
import uk.co.kieraan.mymessages.MyMessages;

public class StaffChatCommand extends MasterCommand {

    MyMessages plugin;

    public StaffChatCommand(MyMessages plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        String staffChatMessage = "";

        if (!isPlayer) {
            sender.sendMessage(ChatColor.RED + "Run this command from ingame.");
            return;
        }

        if (!player.hasPermission("mymessages.staffchat")) {
            player.sendMessage(ChatColor.RED + "Access denied.");
            return;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /" + commandName + " <message>");
            return;
        }

        staffChatMessage += this.plugin.combineSplit(0, args, " ");
        staffChatMessage = this.plugin.format(staffChatMessage);

        if (staffChatMessage != null) {
            for (Player plr : this.plugin.getServer().getOnlinePlayers()) {
                if (plr.hasPermission("mymessages.staffchat")) {
                    plr.sendMessage(ChatColor.GOLD + "[Staff Chat] " + ChatColor.RESET + player.getDisplayName() + ": " + staffChatMessage);
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "Error: Could not send message.");
        }

    }

}
