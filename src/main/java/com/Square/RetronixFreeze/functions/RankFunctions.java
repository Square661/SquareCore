package com.Square.RetronixFreeze.functions;

import com.Square.RetronixFreeze.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeBuilder;
import net.luckperms.api.node.NodeType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

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
        UserManager userManager = api.getUserManager();
        User user = userManager.getUser(player.getUniqueId());
        Group targetGroup = api.getGroupManager().getGroup(rank);
        String rankChangeMessage = "";
        Integer currentRankWeight = Objects.requireNonNull(api.getGroupManager().getGroup(user.getPrimaryGroup())).getWeight().orElse(0);
        Integer targetRankWeight = Objects.requireNonNull(api.getGroupManager().getGroup(rank)).getWeight().orElse(0);


        if (targetGroup != null) {
            // Remove all existing group nodes
            user.data().clear(NodeType.INHERITANCE::matches);
            // If the new rank is higher than the current rank
            if(targetRankWeight > currentRankWeight) {
                rankChangeMessage = ChatColor.GREEN + "You have been promoted to " + rankColourCode(player) + rank + ChatColor.GREEN + " rank.";
            } else if (targetRankWeight < currentRankWeight) {
                rankChangeMessage = ChatColor.GREEN + "You have been" + ChatColor.RED + ChatColor.BOLD + " demoted " + ChatColor.RESET + ChatColor.GREEN + "to" + rankColourCode(player) + rank + " rank.";
            } else if (targetRankWeight == currentRankWeight) {
                rankChangeMessage = ChatColor.GOLD + "You have been set to " + rankColourCode(player) + rank + " rank.";
            }

            // Add the new group node
            user.data().add(Node.builder("group." + rank).build());

            // Save the user data to LuckPerms
            userManager.saveUser(user);

            ChatColor rankColor = rankColourCode(player);
            String rankName = targetGroup.getName();

            player.sendMessage(rankChangeMessage);
            player.sendMessage(ChatColor.GRAY + "Prefix: " + rankPrefix(player));
        } else {
            player.sendMessage(ChatColor.RED + "Failed to set rank. Please try again later.");
        }
    }

    public void setRankUUID(UUID uuid, String rank) {
        UserManager userManager = api.getUserManager();
        User user = userManager.getUser(uuid);
        Group targetGroup = api.getGroupManager().getGroup(rank);
        String rankChangeMessage = "";
        Integer currentRankWeight = Objects.requireNonNull(api.getGroupManager().getGroup(user.getPrimaryGroup())).getWeight().orElse(0);
        Integer targetRankWeight = Objects.requireNonNull(api.getGroupManager().getGroup(rank)).getWeight().orElse(0);

        if (targetGroup != null) {
            // Remove all existing group nodes
            user.data().clear(NodeType.INHERITANCE::matches);
            // If the new rank is higher than the current rank
            if (targetRankWeight > currentRankWeight) {
                rankChangeMessage = ChatColor.GREEN + "You have been promoted to " + rank + " rank.";
            } else if (targetRankWeight < currentRankWeight) {
                rankChangeMessage = ChatColor.GREEN + "You have been" + ChatColor.RED + ChatColor.BOLD + " demoted to " + ChatColor.RESET + rank + " rank.";
            } else if (targetRankWeight == currentRankWeight) {
                rankChangeMessage = ChatColor.GOLD + "You have been set to " + rank + " rank.";
            }

            // Add the new group node
            user.data().add(Node.builder("group." + rank).build());

            // Save the user data to LuckPerms
            userManager.saveUser(user);

            String rankName = targetGroup.getName();

        } else {
            System.out.println("Failed to set rank. Please try again later.");
        }

    }

    public String getRankInformation(Player player) {
        // Get the group name of the player
        String primaryGroup = (Objects.requireNonNull(api.getUserManager().getUser(player.getUniqueId()))).getPrimaryGroup();
        String groupName = Objects.requireNonNull(api.getGroupManager().getGroup(primaryGroup)).getName();
        String permissions = Objects.requireNonNull(api.getUserManager().getUser(player.getUniqueId())).getCachedData().getPermissionData().getPermissionMap().toString();
        String prefix = rankPrefix(player);

        String finalMessage = "&7&m---------------------\n" +
                // Username, rank name, prefix, permissions, rank colour
                "&7Username: &f" + player.getName() + "\n" +
                "&7Rank: " + rankColourCode(player) + groupName + "\n" +
                "&7Prefix: " + prefix + "\n" +
                "&7Permissions: " + permissions + "\n" +
                "&7Rank Colour: " + rankColourCode(player) + "▢▢▢" + "\n" +
                "&7&m---------------------";
        return finalMessage.replace("&", "§");
    }






}
