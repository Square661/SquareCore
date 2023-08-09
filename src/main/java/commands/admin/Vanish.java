package commands.admin;

import com.Square.RetronixFreeze.Main;
import com.Square.RetronixFreeze.functions.Messages;
import com.Square.RetronixFreeze.functions.VanishFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vanish implements CommandExecutor {
    private final Main plugin;
    private final VanishFunctions vanishFunctions;
    public Messages messages;

    public Vanish(Main plugin) {
        this.plugin = plugin;
        this.vanishFunctions = new VanishFunctions(plugin); // Initialize the VanishFunctions instance
        this.messages = new Messages();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("sqc.vanish")) {
                vanishFunctions.toggleVanish(player);
            } else {
                messages.noPermissionMessage(player);
            }
        }
        return true;
    }
}
