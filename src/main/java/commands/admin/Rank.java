package commands.admin;

import com.Square.RetronixFreeze.Main;
import com.Square.RetronixFreeze.functions.RankFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Rank implements CommandExecutor {
    private final Main plugin;

    public Rank(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;
        if(!player.hasPermission("sqc.rank.manage")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            String targetPlayerName = args[1];
            String targetRank = args[2];
            Player targetPlayer = plugin.getServer().getPlayer(targetPlayerName);

            if (targetPlayer == null) {
                player.sendMessage(ChatColor.RED + "Player " + targetPlayerName + " is not online ");
                player.sendMessage(ChatColor.RED + "Searching for player in database to execute offline actions...");
                player.sendMessage(targetPlayerName);
                // Check plugin.SQL.getUUID(targetPlayerName) is not null, if it is null it means they are not found in database, so return true, otherwise getUUID will return the uuid, use that to set the rank
                if (plugin.SQL.getUUID(targetPlayerName) == null) {
                    player.sendMessage(ChatColor.RED + "Player " + targetPlayerName + " not found in database.");
                    return true;
                } else {
                    UUID targetUUID = plugin.SQL.getUUID(targetPlayerName);
                    plugin.rankFunctions.setRankUUID(targetUUID, targetRank);
                    player.sendMessage(ChatColor.GREEN + "You have set " + ChatColor.YELLOW + targetPlayerName + ChatColor.GREEN + "'s rank to " + ChatColor.YELLOW + targetRank);
                    return true;
                }


            }

            plugin.rankFunctions.setRank(targetPlayer, targetRank);
            ChatColor rankColor = RankFunctions.rankColourCode(targetPlayer);
            player.sendMessage(ChatColor.GREEN + "You have set " + ChatColor.YELLOW + targetPlayer.getName() + ChatColor.GREEN + "'s rank to " + rankColor + targetRank);
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "Invalid arguments. Usage: /rank set <player> <rank>");
            return true;
        }
    }
}
