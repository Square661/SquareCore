package commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemode") || cmd.getName().equalsIgnoreCase("gm")) {
            if (!sender.hasPermission("sqc.gamemode")) {
                sender.sendMessage("§c§l(!) You do not have permissions to use this command");
                return true;
            }

            if (args.length == 0 || args.length > 2 || args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§c§l(!) §r§fUsage: /gamemode <gamemode> [player]");
                sender.sendMessage("§c§l(!) §r§fGamemodes: survival (s), creative (c), adventure (a), spectator (sp)");
                sender.sendMessage("§c§l(!) §r§fExample: /gm s [Player]");
                return true;
            }


            GameMode gameMode;
            switch (args[0].toLowerCase()) {
                case "0":
                case "s":
                case "survival":
                    gameMode = GameMode.SURVIVAL;
                    break;
                case "1":
                case "c":
                case "creative":
                case "cr":
                    gameMode = GameMode.CREATIVE;
                    break;
                case "2":
                case "a":
                case "adventure":
                    gameMode = GameMode.ADVENTURE;
                    break;
                case "3":
                case "sp":
                case "spectator":
                    gameMode = GameMode.SPECTATOR;
                    break;
                default:
                    sender.sendMessage("§c§l(!) §r§cInvalid gamemode provided.");
                    return true;
            }

            Player player = null;
            if (args.length == 2) {
                player = Bukkit.getPlayer(args[1]);
                if (player == null) {
                    sender.sendMessage("§c§l(!) Player not found.");
                    return true;
                }
            }

            if (player == null) {
                if (sender instanceof Player) {
                    ((Player) sender).setGameMode(gameMode);
                    sender.sendMessage("§aYour gamemode has been set to " + gameMode.name());
                } else {
                    sender.sendMessage("§c§l(!) You must specify a player if executing this command from the console.");
                }
            } else {
                player.setGameMode(gameMode);
                sender.sendMessage("§a" + player.getName() + "'s gamemode has been set to " + gameMode.name());
                player.sendMessage("§aYour gamemode has been set to " + gameMode.name());
            }

            return true;
        }

        return true;
    }
}
