package dev.insilicon.leonemctest.events;

import dev.insilicon.leonemctest.Leonemctest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class killsystem implements Listener {

    //On kill event
    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null) {
            // Increment killer's kills
            int currentKills = (int) Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(killer.getUniqueId().toString()).getKills();
            Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(killer.getUniqueId().toString()).setKills(currentKills + 1);
            int currentMoney = (int) Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(killer.getUniqueId().toString()).getMoney();
            Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(killer.getUniqueId().toString()).setMoney(currentMoney + 100);

            // Increment victim's deaths
            int currentDeaths = (int) Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(victim.getUniqueId().toString()).getDeaths();
            Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(victim.getUniqueId().toString()).setDeaths(currentDeaths + 1);

            // Ensure to save these changes to your storage system
        }
    }

}
