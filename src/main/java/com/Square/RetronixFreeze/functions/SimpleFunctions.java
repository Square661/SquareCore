package com.Square.RetronixFreeze.functions;

import com.Square.RetronixFreeze.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SimpleFunctions {

    static LuckPerms api = LuckPermsProvider.get();
    private final Main plugin;

    public SimpleFunctions(LuckPerms luckPerms, Main plugin) {

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






}
