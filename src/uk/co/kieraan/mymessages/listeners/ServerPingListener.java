package uk.co.kieraan.mymessages.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import uk.co.kieraan.mymessages.MyMessages;

public class ServerPingListener implements Listener {

    MyMessages plugin;

    public ServerPingListener(MyMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String newMotd = "";
        newMotd += this.plugin.getConfig().getString("server.motd");
        newMotd = this.plugin.formatColors(newMotd);
        event.setMotd(newMotd);
    }

}
