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

   public String rankHelpMessage() {
       String message = ("&7&m---------------------\n" +
               "&c&lRank Help\n" +
               "&7/rank - Get your own rank information\n" +
               "&7/rank <player> - Get another players rank information\n" +
               "&7/rank set <player> <rank> - Set a players rank\n" +
               "&7&m---------------------");
       return message.replace("&", "§");
   }



}
