package dev.insilicon.leonemctest.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import dev.insilicon.leonemctest.Leonemctest;
import dev.insilicon.leonemctest.PlayerDataClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class bal extends BaseCommand {

    @CommandAlias("bal")
    @Description("View your balance")
    public void onBalCommand(Player plr) {
        //Get DB
        PlayerDataClass playerdata = Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(plr.getUniqueId().toString());

        double money = (double) Math.round(playerdata.getMoney() * 100) / 100;

        plr.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Balance:");
        plr.sendMessage(ChatColor.GOLD + "Money: " + ChatColor.WHITE + money);

    }

}
