package org.satellite.dev.progiple.satesafesapi.safes.menus;

import lombok.Getter;
import org.novasparkle.lunaspring.API.Util.utilities.Utils;
import org.satellite.dev.progiple.satesafesapi.Config;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Code {
    private final int length;
    private final String combination;

    private final Map<String, String> picked_combination = new HashMap<>();
    public Code(int length) {
        this.length = Math.min(Math.max(length, 2), 9);
        this.combination = Utils.getRKey((byte) this.length, "012345678");
    }

    public boolean check(String nick) {
        String comb = this.picked_combination.get(nick);
        if (comb == null) return false;
        Config.debug("picked: " + this.picked_combination);
        Config.debug("length: " + this.length);
        Config.debug("combination: " + this.combination);

        return comb.length() == this.length && comb.equalsIgnoreCase(this.combination);
    }

    public void addValue(String nick, int index) {
        this.picked_combination.compute(nick, (k, comb) -> String.format("%s%s", comb == null || comb.isEmpty() ? "" : comb.replace("null", ""), index));
        Config.debug(this.picked_combination);
    }

    public void clear(String nick) {
        this.picked_combination.remove(nick);
    }
}
