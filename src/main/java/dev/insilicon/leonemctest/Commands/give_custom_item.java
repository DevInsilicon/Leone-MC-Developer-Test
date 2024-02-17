package dev.insilicon.leonemctest.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import dev.insilicon.leonemctest.Leonemctest;
import dev.insilicon.leonemctest.PlayerDataClass;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class give_custom_item extends BaseCommand {

    @CommandAlias("givecustomitem")
    @Description("Admin Give Command")
    @CommandPermission("leonemctest.custom_item_give")
    public void onCustomItemGive(Player plr, @Flags("other") Player targetPlayer, @Flags("rare") String item, @Conditions("limits:min=0.1,max=10000") double amount) {


        if (targetPlayer == null) {
            plr.sendMessage("Player not found");
            return;
        }

        NamespacedKey customItemKey = Leonemctest.getPlugin(Leonemctest.class).getGm().getCim().getKeyViaName(item);
        if (customItemKey == null) {
            plr.sendMessage(ChatColor.RED + "Item not found!");
            return;
        }

        ItemStack customItem = Leonemctest.getPlugin(Leonemctest.class).getGm().getCim().createCustomItemStack(customItemKey, 1);
        if (customItem == null) {
            plr.sendMessage(ChatColor.RED + "Fatal error creating item");
            return;
        }


        if (customItem.getItemMeta().getDisplayName() == null) {
            plr.sendMessage(ChatColor.RED + "Fatal error creating item");
            return;
        }

        targetPlayer.getInventory().addItem(customItem);



    }
    
}
