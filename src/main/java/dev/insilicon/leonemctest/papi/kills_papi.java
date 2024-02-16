package dev.insilicon.leonemctest.papi;

import dev.insilicon.leonemctest.Leonemctest;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class kills_papi extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "kills";
    }

    @Override
    public @NotNull String getAuthor() {
        return "insilicon";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
}

@Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        return Leonemctest.getPlugin(Leonemctest.class).getDb().getPlayerData(player.getUniqueId().toString()).getKills() + "";
    }
}
