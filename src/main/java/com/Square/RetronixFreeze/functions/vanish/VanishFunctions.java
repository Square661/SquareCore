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
            player.showPlayer(player); // Remove plugin parameter
            player.sendMessage(ChatColor.GRAY + "Vanish: " + ChatColor.DARK_GREEN + "Off");
        } else {
            vanished.add(player);
            player.hidePlayer(player); // Remove plugin parameter
            player.sendMessage(ChatColor.GRAY + "Vanish: " + ChatColor.GREEN + "On");
        }
    }
}
