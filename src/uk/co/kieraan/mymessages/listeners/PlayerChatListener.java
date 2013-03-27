package uk.co.kieraan.mymessages.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import uk.co.kieraan.mymessages.MyMessages;

public class PlayerChatListener implements Listener {

    MyMessages plugin;

    public PlayerChatListener(MyMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(this.plugin.getConfig().getBoolean("log.chat")) {
            this.plugin.addToLog("chat", event.getPlayer(), event.getMessage());
        }
        
        String newChatMessage = this.plugin.format(event.getMessage());
        event.setMessage(newChatMessage);
    }

}
