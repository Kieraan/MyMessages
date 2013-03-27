package uk.co.kieraan.mymessages.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.kieraan.mymessages.MasterCommand;
import uk.co.kieraan.mymessages.MyMessages;

public class QuitMessageCommand extends MasterCommand {

    MyMessages plugin;

    public QuitMessageCommand(MyMessages plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        String newQuitMessage = "";
        
        if (!isPlayer) {
            sender.sendMessage(ChatColor.RED + "Run this command from ingame.");
        }

        if (!player.hasPermission("mymessages.set.quitmessage")) {
            player.sendMessage(ChatColor.RED + "Access denied.");
            return;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /" + commandName + " <message>");
            return;
        }

        newQuitMessage += this.plugin.combineSplit(0, args, " ");
        this.plugin.getConfig().set("server.quitmessage", newQuitMessage);
        this.plugin.saveConfig();
        for (Player plr : this.plugin.getServer().getOnlinePlayers()) {
            if (plr.hasPermission("mymessages.set.quitmessage") && !plr.equals(player)) {
                plr.sendMessage(ChatColor.GOLD + player.getDisplayName() + ChatColor.GOLD + " changed the server quit message.");
            }
        }
        player.sendMessage(ChatColor.GOLD + "Quit message set to: \"" + ChatColor.RESET + this.plugin.formatColors(newQuitMessage) + ChatColor.GOLD + "\"");
    }

}
