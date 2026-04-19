package org.satellite.dev.progiple.satesafesapi.menu.items;

public interface ICodeItem<T> {
    T getCodeElement();
    boolean isPicked();
    void setPicked(boolean val);
}
