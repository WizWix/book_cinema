package cinema.menu;

import cinema.movie.Movie;
import cinema.util.Color;
import cinema.util.ScannerUtil;

public class AdminMenu extends AbstractMenu {
  private static final AdminMenu instance = new AdminMenu(null);
  private static final String ADMIN_MENU_TEXT = "1. 영화 등록하기 | 2. 영화 목록 보기 | 3. 영화 삭제하기 | b. 메인 메뉴로 이동";

  private AdminMenu(Menu prevMenu) { super(ADMIN_MENU_TEXT, prevMenu); }

  public static AdminMenu getInstance() { return instance; }

  public Menu next() {
    System.out.print("선택> ");
    switch (ScannerUtil.getChar()) {
    case '1' -> {
      createMovie();
      return this;
    }
    case '2' -> {
      printAllMovies();
      return this;
    }
    case '3' -> {
      deleteMovie();
      return this;
    }
    case 'b' -> { return prevMenu; }
    default -> { return this; }
    }
  }

  private void createMovie() {
    System.out.print("새 영화 제목> ");
    String title = ScannerUtil.getStr();
    System.out.print("새 영화 장르> ");
    String genre = ScannerUtil.getStr();

    Movie m = new Movie(title, genre);
    if (m.save()) System.out.println(Color.green + "[새 영화 등록 성공]" + Color.reset);
    else System.out.println(Color.red + "[새 영화 등록 실패]" + Color.reset);
  }

  private boolean printAllMovies() {
    var mvs = Movie.findAll();
    if (mvs.isEmpty()) {
      System.out.println(Color.red + "[영화 목록 비어있음]" + Color.reset);
      return false;
    } else {
      for (Movie movie : mvs) System.out.println(movie);
      return true;
    }
  }

  private void deleteMovie() {
    if (printAllMovies()) {
      System.out.print("삭제할 영화 ID> ");
      String id = ScannerUtil.getStr();
      Movie m = Movie.delete(id);
      if (m != null) System.out.println(Color.green + "[영화 삭제 완료]" + System.lineSeparator() + m + Color.reset);
      else System.out.println(Color.red + "[영화 삭제 실패]" + System.lineSeparator() + "[존재하지 않는 영화]" + Color.reset);
    }
  }
}
