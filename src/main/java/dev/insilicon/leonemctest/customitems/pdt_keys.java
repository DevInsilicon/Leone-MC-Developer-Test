package dev.insilicon.leonemctest.customitems;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class pdt_keys {
    private NamespacedKey jumpWand;
    private List<NamespacedKey> keys = new ArrayList<>();

    public List<NamespacedKey> getKeys() {
        return keys;
    }

    public void setKeys(List<NamespacedKey> keys) {
        this.keys = keys;
    }

    public pdt_keys(Plugin plugin) {
        this.jumpWand = new NamespacedKey(plugin, "jump_wand");
        keys.add(jumpWand);

    }

    public NamespacedKey getJumpWand() {
        return jumpWand;
    }

    public void setJumpWand(NamespacedKey jumpWand) {
        this.jumpWand = jumpWand;
    }
}
