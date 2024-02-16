package dev.insilicon.leonemctest;

import co.aikar.commands.PaperCommandManager;
import dev.insilicon.leonemctest.Commands.*;
import dev.insilicon.leonemctest.events.connectionHandler;
import dev.insilicon.leonemctest.events.killsystem;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class Leonemctest extends JavaPlugin {
    private PaperCommandManager commandManager;
    private CustomDB db;

    @Override
    public void onEnable() {
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


        //Events
        getServer().getPluginManager().registerEvents(new connectionHandler(), this);
        getServer().getPluginManager().registerEvents(new killsystem(), this);

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
