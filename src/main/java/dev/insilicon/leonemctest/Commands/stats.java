package dev.insilicon.leonemctest.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import dev.insilicon.leonemctest.Leonemctest;
import dev.insilicon.leonemctest.PlayerDataClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class stats extends BaseCommand {

    @CommandAlias("stats")
    @Description("View your stats")
    public void onStatsCommand(Player plr) {
        //Get DB
        PlayerDataClass playerdata = Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(plr.getUniqueId().toString());

        String KDR;
        if (playerdata.getDeaths() == 0) {
            KDR = "inf"; // Or any other representation you prefer for infinite KDR
        } else {
            KDR = String.valueOf((double) Math.round((playerdata.getKills() / (double) playerdata.getDeaths()) * 100) / 100);
        }

        double money = (double) Math.round(playerdata.getMoney() * 100) / 100;
        double kills = (double) Math.round(playerdata.getKills() * 100) / 100;
        double deaths = (double) Math.round(playerdata.getDeaths() * 100) / 100;

        plr.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Stats:");
        plr.sendMessage(ChatColor.GOLD + "Money: " + ChatColor.WHITE + money);
        plr.sendMessage(ChatColor.GOLD + "Kills: " + ChatColor.WHITE + kills);
        plr.sendMessage(ChatColor.GOLD + "Deaths: " + ChatColor.WHITE + deaths);
        plr.sendMessage(ChatColor.GOLD + "KDR: " + ChatColor.WHITE + KDR);
    }

}
