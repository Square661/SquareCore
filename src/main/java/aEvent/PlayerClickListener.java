package aEvent;

import com.Square.RetronixFreeze.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerClickListener implements Listener {
    private final Main plugin;

    public PlayerClickListener(Main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
            Player player = event.getPlayer();
//            AutoClickerCheck clickCheck = new AutoClickerCheck(player);
//            Double cps = clickCheck.getCPS();
            Bukkit.getServer().getConsoleSender().sendMessage("Player clicking");
                player.sendMessage(ChatColor.RED + "(!)" + ChatColor.YELLOW + "You passed AC Test");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "CPS " + "11111");
            }


        }
    }
