package org.launchcode.models.forms;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AddMenuItemForm {
    @Valid
    private Menu menu;
    @NotNull
    private int menuId;
    @NotNull
    private int cheeseId;

    //Getters
    public Menu getMenu() {
        return menu;
    }
    public int getMenuId() {
        return menuId;
    }
    public int getCheeseId() {
        return cheeseId;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void setCheeseId(int cheeseId) {
        this.cheeseId = cheeseId;
    }
    //Constructors

    public AddMenuItemForm() { }

    public AddMenuItemForm(Menu menu) {
        this.menu = menu;
    }
}
