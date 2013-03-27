package uk.co.kieraan.mymessages.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.kieraan.mymessages.MyMessages;

public class PlayerQuitListener implements Listener {

    MyMessages plugin;

    public PlayerQuitListener(MyMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(this.plugin.getConfig().getBoolean("log.quit")) {
            this.plugin.addToLog("quit", event.getPlayer(), "left.");
        }
        
        Player player = event.getPlayer();

        String newQuitMessage = "";
        newQuitMessage += this.plugin.getConfig().getString("server.quitmessage");
        newQuitMessage = newQuitMessage.replace("<player>", player.getDisplayName());
        newQuitMessage = this.plugin.format(newQuitMessage);

        if (newQuitMessage == null) {
            this.plugin.getLogger().severe("No custom quit message found, using default instead.");
            return;
        }

        event.setQuitMessage(null);

        for (Player plr : this.plugin.getServer().getOnlinePlayers()) {
            plr.sendMessage(newQuitMessage);
        }
    }

}
