package uk.co.kieraan.mymessages;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.mcstats.MetricsLite;

import uk.co.kieraan.mymessages.commands.*;
import uk.co.kieraan.mymessages.listeners.*;

public class MyMessages extends JavaPlugin {

    public boolean vanishEnabled = false;
    public List<String> rules;
    public List<String> autoMessages;

    @Override
    public void onEnable() {
        if (this.getServer().getPluginManager().isPluginEnabled("VanishNoPacket")) {
            this.getLogger().info("VanishNoPacket found, enabling vanish integration.");
            vanishEnabled = true;
        } else {
            this.getLogger().info("VanishNoPacket not found, custom join/quit messages will be seen by everyone.");
            this.getLogger().info("Get VanishNoPacket at http://dev.bukkit.org/server-mods/vanish/ to enable it.");
            vanishEnabled = false;
        }

        final File check = new File(this.getDataFolder(), "config.yml");
        if (!check.exists()) {
            this.saveDefaultConfig();
            this.reloadConfig();
        }

        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {
            // Stats no worky :(
        }
        this.loadRules();
        this.loadAutoMessages();
        //
        this.getCommand("joinmessage").setExecutor(new JoinMessageCommand(this));
        this.getCommand("quitmessage").setExecutor(new QuitMessageCommand(this));
        this.getCommand("setmotd").setExecutor(new SetMotdCommand(this));
        this.getCommand("staffbroadcast").setExecutor(new StaffBroadcastCommand(this));
        if (this.getConfig().getBoolean("enable_rules")) {
            this.getCommand("rules").setExecutor(new RulesCommand(this));
        }
        //
        this.getServer().getPluginManager().registerEvents(new ServerPingListener(this), this);
        if (vanishEnabled) {
            this.getServer().getPluginManager().registerEvents(new VanishedPlayerJoinListener(this), this);
            this.getServer().getPluginManager().registerEvents(new VanishedPlayerQuitListener(this), this);
        } else {
            this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
            this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        }

        if (this.getConfig().getBoolean("enable_auto_messages")) {
            this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                int currentLine = 0;

                @Override
                public void run() {
                    for (Player plr : MyMessages.this.getServer().getOnlinePlayers()) {
                        String autoMessage = MyMessages.this.formatColors(MyMessages.this.autoMessages.get(this.currentLine));
                        plr.sendMessage(autoMessage);
                    }

                    if (this.currentLine == (MyMessages.this.autoMessages.size() - 1)) {
                        this.currentLine = 0;
                    } else {
                        this.currentLine++;
                    }
                }
            }, 10000, 10000);
        }

        this.getLogger().info("Loaded " + this.getDescription().getName() + " v" + this.getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Disabled " + this.getDescription().getName() + " v" + this.getDescription().getVersion());
    }

    public void loadRules() {
        this.rules = Arrays.asList(this.getConfig().getString("rules").split("\n"));
        if (this.rules == null) {
            this.getConfig().set("enable_rules", false);
            this.getLogger().warning("Could not retrieve rules, disabling rules command.");
        }
    }

    public void loadAutoMessages() {
        this.autoMessages = Arrays.asList(this.getConfig().getString("messages").split("\n"));
        if (this.autoMessages == null) {
            this.getConfig().set("enable_auto_messages", false);
            this.getLogger().warning("Could not retrieve auto messages, disabling them.");
        }
    }

    public String combineSplit(int startIndex, String[] string, String seperator) {
        final StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < string.length; i++) {
            builder.append(string[i]);
            builder.append(seperator);
        }
        builder.deleteCharAt(builder.length() - seperator.length());
        return builder.toString();
    }

    public String formatColors(String toFormat) {
        toFormat = toFormat.replace("&0", "�0");
        toFormat = toFormat.replace("&1", "�1");
        toFormat = toFormat.replace("&2", "�2");
        toFormat = toFormat.replace("&3", "�3");
        toFormat = toFormat.replace("&4", "�4");
        toFormat = toFormat.replace("&5", "�5");
        toFormat = toFormat.replace("&6", "�6");
        toFormat = toFormat.replace("&7", "�7");
        toFormat = toFormat.replace("&8", "�8");
        toFormat = toFormat.replace("&9", "�9");
        //
        toFormat = toFormat.replace("&a", "�a");
        toFormat = toFormat.replace("&b", "�b");
        toFormat = toFormat.replace("&c", "�c");
        toFormat = toFormat.replace("&d", "�d");
        toFormat = toFormat.replace("&e", "�e");
        toFormat = toFormat.replace("&f", "�f");
        //
        toFormat = toFormat.replace("&l", "�L");
        toFormat = toFormat.replace("&o", "�o");
        toFormat = toFormat.replace("&n", "�n");
        toFormat = toFormat.replace("&m", "�m");
        toFormat = toFormat.replace("&k", "�k");

        return toFormat;
    }

}
