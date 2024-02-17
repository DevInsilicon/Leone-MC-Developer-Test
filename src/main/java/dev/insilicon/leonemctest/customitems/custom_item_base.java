package dev.insilicon.leonemctest.customitems;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class custom_item_base {
    private Material base_item;
    private String name;
    private String description;
    private NamespacedKey key;

    void ability(Player plr) {
        //Override this method
    }

    public NamespacedKey getKey() {
        return key;
    }

    public void setKey(NamespacedKey key) {
        this.key = key;
    }

    public Material getBase_item() {
        return base_item;
    }

    public void setBase_item(Material base_item) {
        this.base_item = base_item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
