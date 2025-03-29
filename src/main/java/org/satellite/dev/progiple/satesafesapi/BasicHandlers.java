package org.satellite.dev.progiple.satesafesapi;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.satellite.dev.progiple.satesafesapi.safes.Safe;

public class BasicHandlers implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (block == null || e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Location location = block.getLocation();
        Safe safe = Safe.getSafes().get(location);
        if (safe != null) safe.onClick(e.getPlayer());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Location location = e.getBlock().getLocation();
        Safe safe = Safe.getSafes().get(location);
        if (safe != null) safe.onBreak(e);
    }
}
