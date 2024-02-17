package dev.insilicon.leonemctest.customitems;

import dev.insilicon.leonemctest.Leonemctest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class jump_wand extends custom_item_base {
    private Plugin plugin;
    public jump_wand(Plugin plugin) {

        this.plugin = plugin;

        setName("JumpdWand");
        setDescription("Allows your a leap through the air!");
        setBase_item(Material.STICK);
        setKey(Leonemctest.getPlugin(Leonemctest.class).getKeys().getJumpWand());

    }

    @Override
    public void ability(Player plr) {
        ItemStack item = plr.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey cooldownKey = new NamespacedKey(plugin, "jump_wand_cooldown");

        long currentTime = System.currentTimeMillis();
        long cooldownTime = 5000;

        if (container.has(cooldownKey, PersistentDataType.LONG)) {
            long lastUse = container.get(cooldownKey, PersistentDataType.LONG);
            if (currentTime - lastUse < cooldownTime) {

                plr.sendMessage(ChatColor.YELLOW + "You must wait " + (cooldownTime - (currentTime - lastUse)) / 1000.0 + " seconds before using this item again.");
                return;
            }
        }

        // Item use logic
        final double minForwardSpeed = 2;
        final double minUpwardSpeed = 0.1;
        Vector direction = plr.getLocation().getDirection();

        double forwardSpeed = direction.length();
        if (forwardSpeed < minForwardSpeed) {
            direction.normalize().multiply(minForwardSpeed);
        }

        if (direction.getY() < minUpwardSpeed) {
            direction.setY(minUpwardSpeed);
        }

        plr.setVelocity(direction);

        // Update cooldown
        container.set(cooldownKey, PersistentDataType.LONG, currentTime);
        item.setItemMeta(meta);
    }

}
