package com.Square.RetronixFreeze.functions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {
   public Player noPermissionMessage(Player player) {
    player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
    return player;
   }

   public Player noPermissionMessagePlayer(Player player) {
       player.sendMessage("Sorry, " + player.getName() + ", you do not have permission to use this command!");
       return player;
   }



}
