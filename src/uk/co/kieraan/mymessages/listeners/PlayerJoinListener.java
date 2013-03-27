package uk.co.kieraan.mymessages.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import uk.co.kieraan.mymessages.MyMessages;

public class PlayerJoinListener implements Listener {

    MyMessages plugin;

    public PlayerJoinListener(MyMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String newJoinMessage = "";
        newJoinMessage += this.plugin.getConfig().getString("server.joinmessage");
        newJoinMessage = newJoinMessage.replace("<player>", player.getDisplayName());
        newJoinMessage = this.plugin.formatColors(newJoinMessage);

        if (newJoinMessage.equals("") || newJoinMessage.equals(null)) {
            this.plugin.getLogger().severe("No custom join message found, using default instead.");
            return;
        }

        event.setJoinMessage(null);

        for (Player plr : this.plugin.getServer().getOnlinePlayers()) {
            plr.sendMessage(newJoinMessage);
        }

    }

}
