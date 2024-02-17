package dev.insilicon.leonemctest.customitems;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class custom_item_manager {
    private Plugin plugin;
    private List<custom_item_base> items = new ArrayList<>();

    public custom_item_manager(Plugin plugin) {

        this.plugin = plugin;

        //Register items
        registerItem(new jump_wand(plugin));

    }

    public void handleItemUse(NamespacedKey key, Player plr) {

        for (custom_item_base item : items) {
            if (item.getKey() == key) {
                item.ability(plr);
            }
        }

    }

    public NamespacedKey getKeyViaName(String name) {
        for (custom_item_base item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item.getKey();
            }
        }
        return null;
    }

    private void registerItem(custom_item_base item) {
        items.add(item);
    }

    public ItemStack createCustomItemStack(NamespacedKey key, int amount) {

        for (custom_item_base item : items) {
            if (item.getKey() == key) {
                ItemStack customItem = new ItemStack(item.getBase_item(), amount);
                ItemMeta customMeta = customItem.getItemMeta();
                customMeta.setDisplayName(item.getName());
                customMeta.setLore(List.of(item.getDescription()));
                customMeta.getPersistentDataContainer().set(item.getKey(), PersistentDataType.STRING, "jumpwand");
                customItem.setItemMeta(customMeta);
                return customItem;

            }
        }

        ItemStack nullItem = new ItemStack(Material.WHITE_WOOL, 1);
        ItemMeta nullMeta = nullItem.getItemMeta();
        nullMeta.setDisplayName("Null");
        nullItem.setItemMeta(nullMeta);

        return nullItem;

    }

}
