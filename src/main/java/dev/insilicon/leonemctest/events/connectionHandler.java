package dev.insilicon.leonemctest.events;

import dev.insilicon.leonemctest.Leonemctest;
import dev.insilicon.leonemctest.PlayerDataClass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class connectionHandler implements Listener {
    //On join

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //Check if this is the first time the player has joined
        if (!player.hasPlayedBefore()) {
            Leonemctest.getPlugin(Leonemctest.class).getDb().createPlayerData(new PlayerDataClass(
                    player.getUniqueId().toString(),
                    0,
                    0,
                    0
            ));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //Request data save to mem and disk
        Leonemctest.getPlugin(Leonemctest.class).getDb().writePlayerDataToDISC(Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(player.getUniqueId().toString()), true);
    }
}
