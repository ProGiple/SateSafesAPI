package org.satellite.dev.progiple.satesafesapi.safes.menus;

import lombok.Getter;
import org.novasparkle.lunaspring.API.Util.utilities.LunaMath;
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

        String kit = "123456789";
        StringBuilder endValue = new StringBuilder();

        byte kitSize = (byte) kit.toCharArray().length;
        for (byte i = 0; i < this.length;) {
            char c = kit.charAt(LunaMath.getRandom().nextInt(kitSize));
            if (endValue.toString().contains(String.valueOf(c))) continue;

            endValue.append(c);
            i++;
        }

        this.combination = endValue.toString();
    }

    public boolean check(String nick) {
        String comb = this.picked_combination.get(nick.toLowerCase());
        if (comb == null) return false;
        Config.debug("picked: " + this.picked_combination);
        Config.debug("length: " + this.length);
        Config.debug("combination: " + this.combination);

        return comb.length() == this.length && comb.equalsIgnoreCase(this.combination);
    }

    public void addValue(String nick, int index) {
        this.picked_combination.compute(nick.toLowerCase(),
                (k, comb) -> String.format("%s%s", comb == null || comb.isEmpty() ? "" : comb.replace("null", ""), index + 1));
        Config.debug(this.picked_combination);
    }

    public void clear(String nick) {
        this.picked_combination.remove(nick.toLowerCase());
    }
}
