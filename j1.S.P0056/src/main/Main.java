package main;

import controller.Manager;
import menu.MenuModel;
import menu.MenuView;

public class Main {

    public static void main(String[] args) {
        MenuModel model = new MenuModel();
        MenuView view = new MenuView(model);

        Manager manager = new Manager(model, view);
        manager.startMenu();
    }
}
