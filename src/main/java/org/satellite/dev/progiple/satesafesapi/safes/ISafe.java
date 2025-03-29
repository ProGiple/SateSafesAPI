package org.satellite.dev.progiple.satesafesapi.safes;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.novasparkle.lunaspring.API.Configuration.Configuration;

public interface ISafe {
    void onBreak(BlockBreakEvent e);
    void onComplete(Player player);
    void save(Configuration config, String savePath);
    void onClick(Player player);
}
