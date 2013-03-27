package uk.co.kieraan.mymessages.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.kieraan.mymessages.MasterCommand;
import uk.co.kieraan.mymessages.MyMessages;

public class RulesCommand extends MasterCommand {
    
    MyMessages plugin;

    public RulesCommand(MyMessages plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        int ruleNumber = 1;
        
        if (!isPlayer) {
            sender.sendMessage(ChatColor.RED + "Run this command from ingame.");
            return;
        }
        
        for (String rule : this.plugin.rules) {
            player.sendMessage(ruleNumber + ") " + this.plugin.format(rule));
            ruleNumber++;
        }
    }

}
