package org.satellite.dev.progiple.satesafesapi;

import lombok.Getter;
import org.novasparkle.lunaspring.LunaPlugin;

public final class SateSafesAPI extends LunaPlugin {
    @Getter private static SateSafesAPI plugin;

    @Override
    public void onEnable() {
        plugin = this;

        this.registerListener(new BasicHandlers());
        this.initialize();
    }

    @Override
    public void onDisable() {
    }
}
