package org.satellite.dev.progiple.satesafesapi.menu;

import org.novasparkle.lunaspring.API.menus.IMenu;
import org.satellite.dev.progiple.satesafesapi.func.Code;

import java.util.List;

public interface ICodeMenu<T> extends IMenu {
    Code<T> getCode();
    List<T> getPicked();
    void solve();
    void reset();
}
