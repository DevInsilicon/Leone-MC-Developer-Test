package dev.insilicon.leonemctest;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomDB {
    private Plugin plugin;
    private List<PlayerDataClass> playerDataList = new ArrayList<>();



    public CustomDB(Plugin plugin) {
        this.plugin = plugin;
        initDB();


    }

    private void initDB() {
        Plugin plugin = this.plugin;

        //Create the plugin's folder!
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        //Create user_data folder.
        String dataFolderPath = dataFolder.getPath();
        File user_data = new File(dataFolderPath + "/user_data");
        if (!user_data.exists()) {
            user_data.mkdir();
        }

    }

    private String getPlayerDataPath() {
        return this.plugin.getDataFolder().getPath() + "/user_data";
    }

    public void createPlayerData(PlayerDataClass personalData) {
        //Load the player data from the user_data folder.

        if (personalData == null) {
            return;
        }

        if (personalData.getUuid() == null) {
            return;
        }

        String uuid = personalData.getUuid();
        String playerDataPath = getPlayerDataPath() + "/" + uuid;

        //Create player data folder
        File playerDataFolder = new File(playerDataPath);

        if (playerDataFolder.exists()) {
            plugin.getLogger().warning("Player data already exists for " + uuid);
            return;
        }
        playerDataFolder.mkdir();

        //check stats file
        File statsFile = new File(playerDataPath + "/stats.yml");
        if (!statsFile.exists()) {
            try {
                statsFile.createNewFile();
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to create stats file for " + uuid);
            }
        }

        //Get dev.insilicon.leonemctest.premade_configs.premade_stats_yml.yml and copy it into statsfile
        File copyStatsPremade = copyResourceFromJar("dev.insilicon.leonemctest.premade_configs.premade_stats_yml.yml", "stats.yml");
        if (copyStatsPremade != null) {
            String backupData = "stats:\n   money: 0\n    kills: 0\n    deaths: 0";
            try {
                FileOutputStream out = new FileOutputStream(statsFile);
                out.write(backupData.getBytes());
                out.close();
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to write to stats file for " + uuid);
            }

        }

        //Verify readable by Paper API
        FileConfiguration statsConfig = YamlConfiguration.loadConfiguration(statsFile);
        if (statsConfig == null) {
            plugin.getLogger().warning("Failed to load stats file for " + uuid);
            return;
        }


        //Add it to the playerData array
        this.playerDataList.add(personalData);

        boolean debug = true;
        if (debug) {
            System.out.println("Player data created for " + uuid);
        }
    }


    public PlayerDataClass getPlayerData(String uuid) {
        //Try to attempt to get player data from memory then load it from file if it doesn't exist then add it to memory.
        for (PlayerDataClass playerData : this.playerDataList) {
            if (playerData.getUuid().equals(uuid)) {
                return playerData;
            }
        }

        PlayerDataClass fileData = getFileDataOfPlayer(uuid);
        if (fileData.getUuid() == null) {
            createPlayerData(new PlayerDataClass(uuid, 0, 0, 0));
            fileData = getFileDataOfPlayer(uuid);
            if (fileData.getUuid() == null) {
                plugin.getLogger().warning("Fatal error: Failed multiple times to get player data for " + uuid);
                return null;
            } else {
                this.playerDataList.add(fileData);
                return fileData;

            }
        }
        this.playerDataList.add(fileData);
        return fileData;

    }

    public void writePlayerDataToMEM(PlayerDataClass playerData) {
        for (PlayerDataClass playerData1 : this.playerDataList) {
            if (playerData1.getUuid().equals(playerData.getUuid())) {
                playerData1.setMoney(playerData.getMoney());
                playerData1.setKills(playerData.getKills());
                playerData1.setDeaths(playerData.getDeaths());
                return;
            }
        }
    }

    public void writePlayerDataToDISC(PlayerDataClass playerData, boolean destroyFromMem) {
        String uuid = playerData.getUuid();
        String playerDataPath = getPlayerDataPath() + "/" + uuid;
        File statsFile = new File(playerDataPath + "/stats.yml");
        if (!statsFile.exists()) {
            plugin.getLogger().warning("Stats file not found for " + uuid);
            return;
        }
        FileConfiguration statsConfig = YamlConfiguration.loadConfiguration(statsFile);
        if (statsConfig == null) {
            plugin.getLogger().warning("Failed to load stats file for " + uuid);
            return;
        }
        statsConfig.set("stats.money", playerData.getMoney());
        statsConfig.set("stats.kills", playerData.getKills());
        statsConfig.set("stats.deaths", playerData.getDeaths());
        try {
            statsConfig.save(statsFile);
            if (destroyFromMem) {
                destroyFromMemory(uuid);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to save stats file for " + uuid);
        }
    }

    public void saveAllPlayerDataToDISC() {
        for (PlayerDataClass playerData : this.playerDataList) {
            writePlayerDataToDISC(playerData, true);
        }
    }

    public void destroyFromMemory(String uuid) {
        for (PlayerDataClass playerData : this.playerDataList) {
            if (playerData.getUuid().equals(uuid)) {
                this.playerDataList.remove(playerData);
                return;
            }
        }
    }

    public PlayerDataClass getFileDataOfPlayer(String uuid) {

        File plrDataFolder = new File(getPlayerDataPath() + "/" + uuid);
        if (!plrDataFolder.exists()) {
            System.out.println("Player data not found for " + uuid);
            return new PlayerDataClass(null, 0,0,0);

        }

        File statsFile = new File(plrDataFolder.getPath() + "/stats.yml");
        if (!statsFile.exists()) {
            System.out.println("Stats file not found for " + uuid);
            return new PlayerDataClass(null, 0,0,0);
        }

        FileConfiguration statsConfig = YamlConfiguration.loadConfiguration(statsFile);
        if (statsConfig == null) {
            System.out.println("Failed to load stats file for " + uuid);
            return new PlayerDataClass(null, 0,0,0);
        }

        PlayerDataClass tempPlrData = new PlayerDataClass(
                uuid,
                statsConfig.getDouble("stats.money"),
                statsConfig.getDouble("stats.kills"),
                statsConfig.getDouble("stats.deaths")
        );

        return tempPlrData;



    }


    public File getDataFolder() {
        return plugin.getDataFolder();
    }


    public File copyResourceFromJar(String resourcePath, String outputFileName) {
        String adjustedResourcePath = resourcePath.replace(".", "/");
        File outFile = new File(getDataFolder(), outputFileName);
        if (!outFile.exists()) {
            try (InputStream in = plugin.getResource(adjustedResourcePath);
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
