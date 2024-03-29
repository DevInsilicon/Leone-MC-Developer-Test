package dev.insilicon.leonemctest;

import co.aikar.commands.PaperCommandManager;
import dev.insilicon.leonemctest.Commands.*;
import dev.insilicon.leonemctest.customitems.pdt_keys;
import dev.insilicon.leonemctest.events.connectionHandler;
import dev.insilicon.leonemctest.events.general_manager;
import dev.insilicon.leonemctest.events.killsystem;
import dev.insilicon.leonemctest.papi.deaths_papi;
import dev.insilicon.leonemctest.papi.kills_papi;
import dev.insilicon.leonemctest.papi.money_papi;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class Leonemctest extends JavaPlugin implements @NotNull Listener {
    private PaperCommandManager commandManager;
    private CustomDB db;
    private pdt_keys keys;
    private general_manager gm;

    public general_manager getGm() {
        return gm;
    }

    public void setGm(general_manager gm) {
        this.gm = gm;
    }

    @Override
    public void onEnable() {

        //DEPO

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            /*
             * We register the EventListener here, when PlaceholderAPI is installed.
             * Since all events are in the main class (this class), we simply use "this"
             */
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        //PDT
        this.keys = new pdt_keys(this);

        //DEPO
        // Plugin startup logic
        db = new CustomDB(this);

        //init the db


        //Instantiate the Plugin Manager
        this.commandManager = new PaperCommandManager(this);



        //Commands

        getCommandManager().registerCommand(new stats());
        getCommandManager().registerCommand(new bal());
        getCommandManager().registerCommand(new adminpay());
        getCommandManager().registerCommand(new pay());
        getCommandManager().registerCommand(new kill_effect_test());
        getCommandManager().registerCommand(new give_custom_item());

        //Events
        getServer().getPluginManager().registerEvents(new connectionHandler(), this);
        getServer().getPluginManager().registerEvents(new killsystem(), this);
        this.gm = new general_manager(this);
        getServer().getPluginManager().registerEvents(gm, this);




        //PAPI
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { //
            new kills_papi().register();
            new deaths_papi().register();
            new money_papi().register();
        }



    }

    @Override
    public @NotNull ComponentLogger getComponentLogger() {
        return super.getComponentLogger();
    }

    public pdt_keys getKeys() {
        return keys;
    }

    public PaperCommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        db.saveAllPlayerDataToDISC();
    }

    public CustomDB getDb() {
        return db;
    }

    public void setDb(CustomDB db) {
        this.db = db;
    }

    private File copyResourceFromJar(String resourcePath, String outputFileName) {
        String adjustedResourcePath = resourcePath.replace(".", "/");
        File outFile = new File(getDataFolder(), outputFileName);
        if (!outFile.exists()) {
            try (InputStream in = getResource(adjustedResourcePath);
                 OutputStream out = new FileOutputStream(outFile)) {
                if (in == null) {
                    throw new IllegalArgumentException("Resource not found: " + adjustedResourcePath);
                }
                byte[] buffer = new byte[1024];
                int readBytes;
                while ((readBytes = in.read(buffer)) > 0) {
                    out.write(buffer, 0, readBytes);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outFile;
    }

}
