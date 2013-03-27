package uk.co.kieraan.mymessages.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.kieraan.mymessages.MasterCommand;
import uk.co.kieraan.mymessages.MyMessages;

public class JoinMessageCommand extends MasterCommand {

    MyMessages plugin;

    public JoinMessageCommand(MyMessages plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        String newJoinMessage = "";

        if (!isPlayer) {
            sender.sendMessage(ChatColor.RED + "Run this command from ingame.");
        }

        if (!player.hasPermission("mymessages.set.joinmessage")) {
            player.sendMessage(ChatColor.RED + "Access denied.");
            return;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /" + commandName + " <message>");
            return;
        }

        newJoinMessage += this.plugin.combineSplit(0, args, " ");
        this.plugin.getConfig().set("server.joinmessage", newJoinMessage);
        this.plugin.saveConfig();
        for (Player plr : this.plugin.getServer().getOnlinePlayers()) {
            if (plr.hasPermission("mymessages.set.joinmessage") && !plr.equals(player)) {
                plr.sendMessage(ChatColor.GOLD + player.getDisplayName() + ChatColor.GOLD + " changed the server join message.");
            }
        }
        player.sendMessage(ChatColor.GOLD + "Join message set to: \"" + ChatColor.RESET + this.plugin.formatColors(newJoinMessage) + ChatColor.GOLD + "\"");
    }

}
