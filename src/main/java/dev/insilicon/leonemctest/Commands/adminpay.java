package dev.insilicon.leonemctest.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Flags;
import dev.insilicon.leonemctest.Leonemctest;
import dev.insilicon.leonemctest.PlayerDataClass;
import org.bukkit.entity.Player;

public class adminpay extends BaseCommand {

    @CommandAlias("adminpay")
    @Description("Admin Pay Command")
    public void onAdminPayCommand(Player plr, @Flags("other") Player targetPlayer, @Conditions("limits:min=0.1,max=10000") double amount) {

        //Get Player Class from target name
        //Player targetPlayer = plr.getServer().getPlayer(target.toString());
        if (targetPlayer == null) {
            plr.sendMessage("Player not found");
            return;
        }


        PlayerDataClass targetData = Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(targetPlayer.getUniqueId().toString());
        targetData.setMoney(targetData.getMoney() + amount);

        Leonemctest.getPlugin(Leonemctest.class).getDb().writePlayerDataToMEM(targetData);
        plr.sendMessage("You have given " + targetPlayer.getName() + " " + amount + " coins");



    }
}
