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

            Player targetPlayer = null;
            try {
                targetPlayer = plugin.getServer().getPlayer(targetPlayerName);
                plugin.rankFunctions.setRank(targetPlayer, targetRank);
                ChatColor rankColor = RankFunctions.rankColourCode(targetPlayer);
                player.sendMessage(ChatColor.GREEN + "You have set " + ChatColor.YELLOW + targetPlayer.getName() + ChatColor.GREEN + "'s rank to " + rankColor + targetRank + ChatColor.GREEN + ".");
                return true;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (targetPlayer == null) {
                player.sendMessage(ChatColor.RED + "Player " + targetPlayerName + " is not online ");
                player.sendMessage(ChatColor.GRAY + "Connecting to the database, to execute offline actions...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                UUID targetPlayerUUID = plugin.SQL.getUUID(targetPlayerName);
                if(targetPlayerUUID == null) {
                    player.sendMessage(ChatColor.RED + "Player " + targetPlayerName + " not found in database. This player may not have joined the server before, if they have, please contact a developer." + ChatColor.GRAY + " Debug Code (#8359)");
                    return true;
                }
                try {
                    targetPlayer = plugin.getServer().getOfflinePlayer(targetPlayerUUID).getPlayer();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    player.sendMessage(ChatColor.RED + "Player " + targetPlayerName + " not found in database. This player may not have joined the server before, if they have, please contact a developer." + ChatColor.GRAY + " Debug Code (#5493)");
                    return true;
                }

                    return true;
                } else {
                    plugin.rankFunctions.setRank(targetPlayer, targetRank);
                }

                ChatColor rankColor = RankFunctions.rankColourCode(targetPlayer);
                player.sendMessage(ChatColor.GREEN + "You have set " + ChatColor.YELLOW + targetPlayer.getName() + ChatColor.GREEN + "'s rank to " + rankColor + targetRank + ChatColor.GREEN + ".");

            } else {
                player.sendMessage(ChatColor.RED + "Usage: /rank set <player> <rank>");
            }
        return false;
    }
}

