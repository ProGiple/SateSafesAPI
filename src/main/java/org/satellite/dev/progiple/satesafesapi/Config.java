package org.satellite.dev.progiple.satesafesapi;

import lombok.experimental.UtilityClass;
import org.novasparkle.lunaspring.API.Configuration.IConfig;

@UtilityClass
public class Config {
    private final IConfig config;
    static {
        config = new IConfig(SateSafesAPI.getPlugin());
    }

    public void reload() {
        config.reload(SateSafesAPI.getPlugin());
    }

    public void debug(Object o) {
        if (config.getBoolean("debug")) SateSafesAPI.getPlugin().info(o.toString());
    }
}
