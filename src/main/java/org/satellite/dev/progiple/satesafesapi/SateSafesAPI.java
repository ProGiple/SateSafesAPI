package org.satellite.dev.progiple.satesafesapi;

import lombok.Getter;
import org.novasparkle.lunaspring.LunaPlugin;

public final class SateSafesAPI extends LunaPlugin {
    @Getter private static SateSafesAPI plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();

        this.registerListener(new BasicHandlers());
        this.registerCommand(new Command(), "satesafesapi");
        this.initialize();
    }

    @Override
    public void onDisable() {
    }
}
