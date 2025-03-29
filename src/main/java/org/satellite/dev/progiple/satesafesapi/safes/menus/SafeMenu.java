package org.satellite.dev.progiple.satesafesapi.safes.menus;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.Events.CooldownPrevent;
import org.novasparkle.lunaspring.API.Menus.IMenu;
import org.novasparkle.lunaspring.API.Util.utilities.Utils;
import org.satellite.dev.progiple.satesafesapi.Config;
import org.satellite.dev.progiple.satesafesapi.safes.Safe;
import org.satellite.dev.progiple.satesafesapi.safes.menus.buttons.CodeButton;

import java.util.List;

@Getter
public class SafeMenu implements IMenu {
    private final CooldownPrevent<Integer> cooldownPrevent = new CooldownPrevent<>();

    private final Player player;
    private final Inventory inventory;
    private final List<CodeButton> buttonList;
    private final Code code;
    private final Safe safe;

    @SuppressWarnings("deprecation")
    public SafeMenu(Player player, String title, List<CodeButton> buttonList, Safe safe) {
        this.player = player;
        this.buttonList = buttonList;
        this.safe = safe;
        this.code = this.safe.getCode();
        this.inventory = Bukkit.createInventory(player, InventoryType.DISPENSER, Utils.color(title));
        this.cooldownPrevent.setCooldownMS(75);
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        this.buttonList.forEach(b -> b.insert(this));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        if (item == null || item.getType() == Material.AIR || this.isCancelled(e, e.getSlot())) return;

        e.setCancelled(true);
        for (CodeButton button : this.buttonList) {
            Config.debug(button.getId());
            if (button.checkId(item)) {
                Config.debug("!");
                button.onClick(e);
                return;
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        this.code.clear(this.getPlayer().getName());
    }

    @Override
    public boolean isCancelled(Cancellable cancellable, int i) {
        return this.cooldownPrevent.isCancelled(cancellable, i);
    }
}
