package commands.player;

import com.Square.RetronixFreeze.Main;
import com.Square.RetronixFreeze.functions.SimpleFunctions;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class List implements CommandExecutor {
    LuckPerms api = LuckPermsProvider.get();
    private final Main plugin;

    public List(LuckPerms luckPerms, Main plugin) {

        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (cmd.getName().equalsIgnoreCase("list")) {
            Player[] players = plugin.getServer().getOnlinePlayers().toArray(new Player[0]);
            sender.sendMessage(ChatColor.YELLOW + "Players online (" + players.length + "):");
            for (Player player : players) {
                String prefix = api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix();
                if(prefix == null) {
                    prefix = "&7";
                    prefix = prefix.replace("&", "§");
                } else {
                    prefix = prefix.replace("&", "§");
                }
                // Call the RankColourCode function from the Main class, which returns ChatColor for the players rank, use that to display the players name in that colour
                sender.sendMessage(ChatColor.GRAY + "• " + prefix + " " + ChatColor.RESET + SimpleFunctions.rankColourCode(player) + player.getName());
                // Call the RankColourCode function in the SimpleFunctions class, which returns ChatColor for the players rank, use that to display the players name in that colour

            }
            return true;
        }
        return true;
    }
}


















            //            Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
//            sender.sendMessage(ChatColor.YELLOW + "Players online (" + players.length + "):");
//            // TODO - Make this display the player's name in the same colour as their rank.
//            // TODO - Possibly check their rank and then set the colour of the name accordingly based on a switch statement which can be in their main class and accessible from all classes.
//
//            for (Player player : players) {
//                String prefix = api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix();
//                player.sendMessage(ChatColor.YELLOW + "Players online (" + players.length + "):");
//                prefix = prefix.replace("&", "§");
//                sender.sendMessage(prefix + " " + player.getName());
//
//
//                return true;
//            }
//        }
//        return true;
//    }
//

