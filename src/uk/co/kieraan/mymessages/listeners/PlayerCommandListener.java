package uk.co.kieraan.mymessages.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import uk.co.kieraan.mymessages.MyMessages;

public class PlayerCommandListener implements Listener {

    MyMessages plugin;

    public PlayerCommandListener(MyMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if(this.plugin.getConfig().getBoolean("log.command")) {
            this.plugin.addToLog("command", event.getPlayer(), event.getMessage());
        }
    }

}
