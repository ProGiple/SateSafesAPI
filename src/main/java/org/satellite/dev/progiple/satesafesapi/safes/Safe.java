package org.satellite.dev.progiple.satesafesapi.safes;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.novasparkle.lunaspring.API.Configuration.Configuration;
import org.novasparkle.lunaspring.API.Util.utilities.Utils;
import org.novasparkle.lunaspring.LunaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Safe implements ISafe {
    @Getter private static final Map<Location, Safe> safes = new HashMap<>();

    private final Location location;
    private final Material material;
    private final LunaPlugin usedPlugin;

    private final String id = Utils.getRKey((byte) 18);
    @Setter private int code_length = 3;
    public Safe(LunaPlugin lunaPlugin, Location location, Material material) {
        this.location = location;
        this.usedPlugin = lunaPlugin;
        this.material = material;

        this.location.getBlock().setType(this.material);
        safes.put(this.location, this);
    }

    @Override
    public void save(Configuration config, String path) {
        String endedPath = path + "." + this.id;
        config.setLocation(endedPath + ".location", this.location, false, true);
        config.setMaterial(endedPath + ".material", this.material);
        config.save();
    }
}