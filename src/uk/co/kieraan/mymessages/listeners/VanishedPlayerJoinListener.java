package uk.co.kieraan.mymessages.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kitteh.vanish.staticaccess.VanishNoPacket;
import org.kitteh.vanish.staticaccess.VanishNotLoadedException;

import uk.co.kieraan.mymessages.MyMessages;

public class VanishedPlayerJoinListener implements Listener {

    MyMessages plugin;

    public VanishedPlayerJoinListener(MyMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) throws VanishNotLoadedException {
        Player player = event.getPlayer();

        String newJoinMessage = "";
        newJoinMessage += this.plugin.getConfig().getString("server.joinmessage");
        newJoinMessage = this.plugin.format(newJoinMessage, player.getDisplayName());

        if (newJoinMessage == null) {
            this.plugin.getLogger().severe("No custom join message found, using default instead.");
            return;
        }

        event.setJoinMessage(null);

        for (Player plr : this.plugin.getServer().getOnlinePlayers()) {
            if (VanishNoPacket.isVanished(player.getName())) {
                if (plr.hasPermission("mymessages.vanish.join")) {
                    plr.sendMessage(newJoinMessage);
                } 
            } else {
                plr.sendMessage(newJoinMessage);
            }
        }

    }

}
