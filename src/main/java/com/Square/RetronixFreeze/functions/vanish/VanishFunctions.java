package com.Square.RetronixFreeze.functions.vanish;

import com.Square.RetronixFreeze.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VanishFunctions {
    private final Main plugin;

    public VanishFunctions(Main plugin) {
        this.plugin = plugin;
    }

    public static ArrayList<Player> vanished = new ArrayList<>();

    public boolean isVanished(Player player) {
        return vanished.contains(player);
    }

    public void toggleVanish(Player player) {
        if (isVanished(player)) {
            vanished.remove(player);
            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                onlinePlayer.showPlayer(player);
            }
            player.sendMessage(ChatColor.GRAY + "Vanish: " + ChatColor.DARK_GREEN + "Off");
        } else {
            vanished.add(player);
            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission("sqc.vanish.seevanished")) {
                    onlinePlayer.hidePlayer(player);
                }
            }
            player.sendMessage(ChatColor.GRAY + "Vanish: " + ChatColor.GREEN + "On");
        }
    }
}
