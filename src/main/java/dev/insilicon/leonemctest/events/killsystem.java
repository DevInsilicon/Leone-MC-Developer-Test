package dev.insilicon.leonemctest.events;

import dev.insilicon.leonemctest.Leonemctest;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

            killeffect(victim, 100, 1, 1);
        }
    }


    public void killeffect(Player plr, int numberOfParticles, double radius, double speed) {
        Location center = plr.getLocation();
        for (int i = 0; i < numberOfParticles; i++) {
            double phi = Math.random() * Math.PI;
            double theta = Math.random() * 2 * Math.PI;
            double x = radius * Math.cos(theta) * Math.sin(phi);
            double y = radius * Math.cos(phi);
            double z = radius * Math.sin(theta) * Math.sin(phi);

            center.getWorld().spawnParticle(Particle.FLAME, center.clone().add(x, y, z), 1, 1, 1, 1, speed);
            fireworksound(plr);
        }
    }

    public void fireworksound(Player plr) {
        plr.getWorld().playSound(plr.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1F, 1F);
    }

}
