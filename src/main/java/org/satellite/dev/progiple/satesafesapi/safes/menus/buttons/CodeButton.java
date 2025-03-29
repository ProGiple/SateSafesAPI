package org.satellite.dev.progiple.satesafesapi.safes.menus.buttons;

import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.novasparkle.lunaspring.API.Menus.IMenu;
import org.novasparkle.lunaspring.API.Menus.Items.Item;
import org.novasparkle.lunaspring.API.Menus.MenuManager;
import org.satellite.dev.progiple.satesafesapi.Config;
import org.satellite.dev.progiple.satesafesapi.safes.menus.Code;
import org.satellite.dev.progiple.satesafesapi.safes.menus.SafeMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void setPicked(ConfigurationSection section) {
        this.picked_lore = new ArrayList<>(section.getStringList("lore"));
        this.picked_material = Material.getMaterial(Objects.requireNonNull(section.getString("material")));
        this.picked_name = section.getString("displayName");
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        IMenu iMenu = MenuManager.getActiveInventories().get(e.getInventory());
        Player player = iMenu.getPlayer();

        String nick = player.getName().toLowerCase();
        if (iMenu instanceof SafeMenu safeMenu) {
            Config.debug("true");
            Code code = safeMenu.getCode();
            Config.debug(code.getPicked_combination());
            Config.debug(code.getPicked_combination().containsKey(nick));
            if (code.getPicked_combination().containsKey(nick) && code.getPicked_combination().get(nick).contains(String.valueOf(this.getSlot() + 1))) return;

            code.addValue(nick, this.getSlot());
            if (code.check(nick)) {
                safeMenu.getSafe().onComplete(player);
                player.closeInventory();
                return;
            }

            if (this.picked_name != null) this.setDisplayName(this.picked_name);
            if (this.picked_material != null) this.setMaterial(this.picked_material);
            if (this.picked_lore != null) this.setLore(this.picked_lore);

            if (code.getPicked_combination().get(nick).length() >= code.getLength()) {
                safeMenu.getButtonList().forEach(b -> {
                    b.reset_button();
                    b.insert(safeMenu);
                });

                safeMenu.getSafe().onBadAttempt(player);
                code.clear(player.getName());
            } else this.insert(safeMenu);
        }
    }
}
