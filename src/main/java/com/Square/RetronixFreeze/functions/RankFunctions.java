package com.Square.RetronixFreeze.functions;

import com.Square.RetronixFreeze.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RankFunctions {

    static LuckPerms api = LuckPermsProvider.get();
    private final Main plugin;

    public RankFunctions(LuckPerms luckPerms, Main plugin) {

        this.plugin = plugin;
    }

    public static ChatColor rankColourCode(Player player) {
        String primaryGroup = (Objects.requireNonNull(api.getUserManager().getUser(player.getUniqueId()))).getPrimaryGroup();
        // Get the group name of the player
        String group = Objects.requireNonNull(api.getGroupManager().getGroup(primaryGroup)).getName();
        switch (group) {
            case "admin":
                return ChatColor.RED;
            case "mod":
                return ChatColor.BLUE;
            case "default":
                return ChatColor.GRAY;
        }
        return ChatColor.WHITE;
    }

    // Function to get a players rank prefix
    public static String rankPrefix(Player player) {
        String prefix = api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix();
        if (prefix == null) {
            prefix = "&7";
        }
        prefix = prefix.replace("&", "§");
        return prefix;
    }

    public void setRank(Player player, String rank) {
        api.getUserManager().modifyUser(player.getUniqueId(), user -> {
            user.setPrimaryGroup(rank);
        });
        // Check if the rank they are being set to is higher weight or lower weight
        // If lower, send message saying they have been demoted to xxx
        // If higher they have been promoted to xxx
        // If same, they have been set to xxx
        Integer currentWeight = api.getGroupManager().getGroup(Objects.requireNonNull(api.getUserManager().getUser(player.getUniqueId())).getPrimaryGroup()).getWeight().orElse(0);
        Integer newWeight = api.getGroupManager().getGroup(rank).getWeight().orElse(0);
        ChatColor rankColour = rankColourCode(player);
        // hi nugs
        if(newWeight > currentWeight) {
            player.sendMessage(ChatColor.GREEN + "You have been promoted to " + rankColour + rank);
            api.getUserManager().getUser(player.getName()).setPrimaryGroup(rank);
        } else if(newWeight < currentWeight) {
            player.sendMessage(ChatColor.RED + "You have been demoted to " + rankColour + rank);
            api.getUserManager().getUser(player.getName()).setPrimaryGroup(rank);
        } else {
            player.sendMessage(ChatColor.GREEN + "You have been set to " + rankColour + rank);
            api.getUserManager().getUser(player.getName()).setPrimaryGroup(rank);
        }
    }






}
