package org.satellite.dev.progiple.satesafesapi.func;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Code<T> {
    public final int length;
    @Getter private final List<T> combination;
    public Code(int length, List<T> combination) {
        this.length = length;
        this.combination = combination;
    }

    public Code(int length, CodeMask<T> mask) {
        this.length = length;
        this.combination = new ArrayList<>();
        for (int i = 0; i < length; i++) this.combination.add(mask.generate(length, i));
    }

    @SafeVarargs
    public final boolean check(T... vals) {
        if (vals.length != length) return false;

        for (int i = 0; i < length; i++)
            if (vals[i] != combination.get(i))
                return false;
        return true;
    }

    public boolean check(List<T> vals) {
        if (vals.size() != length) return false;

        for (int i = 0; i < length; i++)
            if (!Objects.equals(vals.get(i), combination.get(i)))
                return false;
        return true;
    }

    public boolean easyCheck(Collection<T> vals) {
        if (vals.size() != length) return false;

        for (int i = 0; i < length; i++)
            if (!vals.contains(combination.get(i)))
                return false;
        return true;
    }
}
