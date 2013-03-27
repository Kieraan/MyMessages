package uk.co.kieraan.mymessages.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kitteh.vanish.staticaccess.VanishNoPacket;
import org.kitteh.vanish.staticaccess.VanishNotLoadedException;

import uk.co.kieraan.mymessages.MyMessages;

public class VanishedPlayerQuitListener implements Listener {

    MyMessages plugin;

    public VanishedPlayerQuitListener(MyMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) throws VanishNotLoadedException {
        Player player = event.getPlayer();

        String newQuitMessage = "";
        newQuitMessage += this.plugin.getConfig().getString("server.quitmessage");
        newQuitMessage = newQuitMessage.replace("<player>", player.getDisplayName());
        newQuitMessage = this.plugin.formatColors(newQuitMessage);

        if (newQuitMessage.equals("") || newQuitMessage.equals(null)) {
            this.plugin.getLogger().severe("No custom quit message found, using default instead.");
            return;
        }

        event.setQuitMessage(null);

        for (Player plr : this.plugin.getServer().getOnlinePlayers()) {
            if (VanishNoPacket.isVanished(player.getName())) {
                if (plr.hasPermission("mymessages.vanish.quit")) {
                    plr.sendMessage(newQuitMessage);
                }
            } else {
                plr.sendMessage(newQuitMessage);
            }
        }
    }

}
