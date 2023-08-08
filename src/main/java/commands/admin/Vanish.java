package commands.admin;

import com.Square.RetronixFreeze.Main;
import com.Square.RetronixFreeze.functions.vanish.VanishFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vanish implements CommandExecutor {
    private final Main plugin;
    private final VanishFunctions vanishFunctions;

    public Vanish(Main plugin) {
        this.plugin = plugin;
        this.vanishFunctions = new VanishFunctions(plugin); // Initialize the VanishFunctions instance
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("sqc.vanish")) {
                vanishFunctions.toggleVanish(player);
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
        }
        return true;
    }
}
