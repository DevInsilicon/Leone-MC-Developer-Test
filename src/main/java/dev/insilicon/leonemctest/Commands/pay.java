package dev.insilicon.leonemctest.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Flags;
import dev.insilicon.leonemctest.Leonemctest;
import dev.insilicon.leonemctest.PlayerDataClass;
import org.bukkit.entity.Player;

public class pay extends BaseCommand {

    @CommandAlias("pay")
    @Description("Pay Command")
    public void onPayCommand(Player plr, @Flags("other") Player targetPlayer, @Conditions("limits:min=0.1,max=10000") double amount) {

        //Player targetPlayer = plr.getServer().getPlayer(target.toString());
        if (targetPlayer == null) {
            plr.sendMessage("Player not found");
            return;
        }

        if (plr.getUniqueId().toString().equals(targetPlayer.getUniqueId().toString())) {
            plr.sendMessage("You cannot pay yourself");
            return;
        }

        //Get Playerdata of each player

        PlayerDataClass playerdataTarget  = Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(targetPlayer.getUniqueId().toString());
        PlayerDataClass playerdataPlr = Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(plr.getUniqueId().toString());

        if (playerdataPlr.getMoney() < amount) {
            plr.sendMessage("You do not have enough money");
            return;
        }

        playerdataPlr.setMoney(playerdataPlr.getMoney() - amount);
        playerdataTarget.setMoney(playerdataTarget.getMoney() + amount);


    }
}
