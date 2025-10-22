package cinema;

import cinema.menu.MainMenu;
import cinema.menu.Menu;

public class Main {
  public static void main(String[] args) {
    System.out.println("Initiating Program...");
    Menu menu = MainMenu.getInstance();
    while (menu != null) {
      menu.print();
      menu = menu.next();
    }
    System.out.println("Exiting Program...");
  }
}
