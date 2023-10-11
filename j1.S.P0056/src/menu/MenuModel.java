package menu;

public class MenuModel {
    private String[] menuItems = {
        "Add worker",
        "Increase salary for worker",
        "Decrease salary for worker",
        "Show adjusted salary worker information",
        "Exit"
    };

    private int selectedMenuItem;

    public String[] getMenuItems() {
        return menuItems;
    }

    public void setSelectedMenuItem(int selectedMenuItem) {
        this.selectedMenuItem = selectedMenuItem;
    }

    public int getSelectedMenuItem() {
        return selectedMenuItem;
    }
}
