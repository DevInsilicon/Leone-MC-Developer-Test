package dev.insilicon.leonemctest.events;

import dev.insilicon.leonemctest.Leonemctest;
import dev.insilicon.leonemctest.customitems.custom_item_manager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class general_manager implements Listener {
    private custom_item_manager cim;
    private List<NamespacedKey> keys = new ArrayList<>();
    public general_manager(Plugin plugin) {
        this.cim = new custom_item_manager(plugin);


        keys = Leonemctest.getPlugin(Leonemctest.class).getKeys().getKeys();
    }


    @EventHandler
    public void onUseItem(PlayerInteractEvent event) {

        ItemStack item = event.getItem();
        if (item != null && item.hasItemMeta()) {

            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();

            if (container.has(new NamespacedKey(Leonemctest.getPlugin(Leonemctest.class), "jump_wand"), PersistentDataType.STRING)) {

                cim.handleItemUse(Leonemctest.getPlugin(Leonemctest.class).getKeys().getJumpWand(), event.getPlayer());
            }
        }
    }

    public custom_item_manager getCim() {
        return cim;
    }

    public void setCim(custom_item_manager cim) {
        this.cim = cim;
    }
}
