package cinema.menu;

public abstract class AbstractMenu implements Menu {
  protected String menuText;
  protected Menu prevMenu;

  public AbstractMenu(final String menuText, final Menu prevMenu) {
    this.menuText = menuText;
    this.prevMenu = prevMenu;
  }

  // show menu
  public void print() {
    System.out.println("=".repeat(menuText.length()));
    System.out.println(menuText);
    System.out.println("=".repeat(menuText.length()));
  }

  public void setPrevMenu(Menu prevMenu) { this.prevMenu = prevMenu; }
}
