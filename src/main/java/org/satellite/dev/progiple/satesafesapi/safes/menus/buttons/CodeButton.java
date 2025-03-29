package org.satellite.dev.progiple.satesafesapi.safes.menus.buttons;

import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.novasparkle.lunaspring.API.Menus.IMenu;
import org.novasparkle.lunaspring.API.Menus.Items.Item;
import org.novasparkle.lunaspring.API.Menus.MenuManager;
import org.satellite.dev.progiple.satesafesapi.safes.menus.Code;
import org.satellite.dev.progiple.satesafesapi.safes.menus.SafeMenu;

import java.util.List;

@Setter
public class CodeButton extends Item {
    private String picked_name;
    private Material picked_material;
    private List<String> picked_lore;

    private final String default_name;
    private final Material default_material;
    private final List<String> default_lore;
    public CodeButton(Material material, String displayName, List<String> lore, byte index) {
        super(material, displayName, lore, index + 1, index);

        this.default_lore = lore;
        this.default_material = material;
        this.default_name = displayName;
    }

    public CodeButton(ConfigurationSection section, int index) {
        super(section, index);
        this.setAmount(index + 1);

        this.default_name = this.getDisplayName();
        this.default_lore = this.getLore();
        this.default_material = this.getMaterial();
    }

    public CodeButton(Material material, int index) {
        super(material, index + 1);
        this.setSlot((byte) index);

        this.default_name = this.getDisplayName();
        this.default_lore = this.getLore();
        this.default_material = this.getMaterial();
    }

    public void reset_button() {
        this.setMaterial(this.default_material);
        this.setLore(this.default_lore);
        this.setDisplayName(this.default_name);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        IMenu iMenu = MenuManager.getActiveInventories().get(e.getInventory());

        Player player = iMenu.getPlayer();
        if (iMenu instanceof SafeMenu safeMenu) {
            Code code = safeMenu.getCode();
            if (code.getPicked_combination().contains(String.valueOf(this.getSlot()))) return;

            if (code.getLength() == code.getPicked_combination().length()) {
                if (!code.check()) {
                    safeMenu.getButtonList().forEach(CodeButton::reset_button);
                    safeMenu.getSafe().onBadAttempt(player);
                    code.clear();
                }
            } else {
                if (this.picked_name != null) this.setDisplayName(this.picked_name);

                if (this.picked_material != null) this.setMaterial(this.picked_material);

                if (this.picked_lore != null) this.setLore(this.picked_lore);

                code.addValue(this.getSlot());
                this.insert(safeMenu);
            }

            if (code.check()) {
                safeMenu.getSafe().onComplete(player);
                player.closeInventory();
            }
        }
    }
}
