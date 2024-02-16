package dev.insilicon.leonemctest.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Vector;

public class kill_effect_test extends BaseCommand {



    @CommandAlias("killeffecttest")
    @Description("Test kill effect")
    public void onKillEffectTestCommand(Player plr) {
        killeffect(plr, 100, 1, 1);
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
