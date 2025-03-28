package org.satellite.dev.progiple.satesafesapi.safes.menus;

import lombok.Getter;
import org.novasparkle.lunaspring.API.Util.utilities.Utils;

@Getter
public class Code {
    private final int length;
    private final String combination;

    private String picked_combination = "";
    public Code(int length) {
        this.length = Math.min(Math.max(length, 2), 9);
        this.combination = Utils.getRKey((byte) this.length, "012345678");
    }

    public boolean check() {
        return this.picked_combination.length() == this.length && this.picked_combination.equalsIgnoreCase(this.combination);
    }

    public void addValue(int index) {
        this.picked_combination = String.format("%s%s", this.picked_combination, index);
    }

    public void clear() {
        this.picked_combination = "";
    }
}
