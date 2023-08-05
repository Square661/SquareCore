package com.Square.RetronixFreeze;

import aEvent.PlayerClickListener;
import com.Square.RetronixFreeze.SQL.MySQL;
import commands.admin.*;
import commands.player.HealthCheck;
import commands.player.Help;
import commands.player.List;
import commands.player.Spawn;
import events.JoinEvent;
import events.LeaveEvent;
import events.PlayerMessageEvent;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.*;

public class Main extends JavaPlugin {

    // Maintenance mode boolean
    public static boolean maintenance = false;

    public MySQL SQL;

    @Override
    public void onEnable() {


        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[SquareCore] has been enabled");
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[RF] Debug mode has been enabled.");

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();

        } else {
            Bukkit.getLogger().info("LuckPerms not found!");
        }


        this.SQL = new MySQL();
        try {
            SQL.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("Database connection failed.");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("Database connection failed.");
        }

        if(SQL.isConnected()) {
            Bukkit.getLogger().info("Database is connected!");
        }


        // Admin commands
        getCommand("gamemode").setExecutor(new Gamemode());
        getCommand("heal").setExecutor(new Heal(this));
        getCommand("checksql").setExecutor(new SQLCheck(SQL));
        getCommand("maintenance").setExecutor(new Maintenance(this));
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("checkrank").setExecutor(new RankCheck());


        // Player commands
        getCommand("help").setExecutor(new Help(this));
        getCommand("checkhealth").setExecutor(new HealthCheck());
        getCommand("list").setExecutor(new List(LuckPermsProvider.get(), this));
        getCommand("spawn").setExecutor(new Spawn());





        // Events
        getServer().getPluginManager().registerEvents(new JoinEvent(this, SQL), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(this, SQL), this);
        getServer().getPluginManager().registerEvents(new PlayerMessageEvent(), this);

        // getServer().getPluginManager().registerEvents(new PlayerClickListener(this), this);




    }




    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SquareCore] has been disabled");

        SQL.disconnect();
        Bukkit.getLogger().info("Database has disconnected.");

    }




}

