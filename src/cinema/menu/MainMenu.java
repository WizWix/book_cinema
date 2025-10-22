package cinema.menu;

import cinema.movie.Movie;
import cinema.movie.Seats;
import cinema.reserve.Reservation;
import cinema.util.Color;
import cinema.util.ScannerUtil;

import java.util.ArrayList;

public class MainMenu extends AbstractMenu {
  private static final MainMenu instance = new MainMenu(null);
  private static final String MAIN_MENU_TEXT = "1. 영화 예매하기 | 2. 예매 확인하기 | 3. 예매 취소하기 | 4. 관리자 메뉴로 이동 | q. 종료";

  private MainMenu(Menu prevMenu) { super(MAIN_MENU_TEXT, prevMenu); }

  public static MainMenu getInstance() { return instance; }

  public Menu next() {
    System.out.print("선택> ");
    switch (ScannerUtil.getChar()) {
    case '1' -> {
      reserve();
      return this;
    }
    case '2' -> {
      checkReservation();
      return this;
    }
    case '3' -> {
      cancelReservation();
      return this;
    }
    case '4' -> {
      if (checkAdminPassword()) {
        AbstractMenu am = AdminMenu.getInstance();
        am.setPrevMenu(this);
        return am;
      } else return this;
    }
    case 'q' -> { return prevMenu; }
    default -> { return this; }
    }
  }

  private void reserve() {
    var mvs = Movie.findAll();
    if (mvs.isEmpty()) {
      System.out.println(Color.red + "[빈 영화 목록]" + Color.reset);
      return;
    }
    for (int i = 0 ; i < mvs.size() ; i++) System.out.println((i + 1) + " " + mvs.get(i).toString());
    System.out.print("예매할 영화 선택> ");
    Movie m = mvs.get(ScannerUtil.getInt() - 1);
    ArrayList<Reservation> rs = Reservation.findByMovieId(String.valueOf(m.id));
    Seats s = new Seats(rs);
    s.show();
    System.out.print("예매할 좌석 선택 (예: 'a1')> ");
    String input = ScannerUtil.getStr();
    Seats.MarkStatus res = s.mark(input);
    if (res.isSuccess) {
      Reservation r = new Reservation(m.id, m.title, input);
      r.save();
      System.out.println(Color.green + "[좌석 예약 성공]" + System.lineSeparator() + "[발급 번호: " + r.getId() + "]" + Color.reset);
    } else {
      System.out.println(Color.red + "[좌석 예약 실패]" + System.lineSeparator() + "[" + res.reason + "]" + Color.reset);
    }
  }

  private void checkReservation() {
    System.out.print("발급 번호 입력> ");
    long input = ScannerUtil.getLong();
    Reservation r = Reservation.findById(String.valueOf(input));
    if (r == null) System.out.println(Color.red + "[해당하는 예매가 없습니다]" + Color.reset);
    else System.out.println(Color.green + "[예매 정보] " + r + Color.reset);
  }

  private void cancelReservation() {
    System.out.print("발급 번호 입력> ");
    long input = ScannerUtil.getLong();
    Reservation r = Reservation.findById(String.valueOf(input));
    if (r == null) System.out.println(Color.red + "[해당하는 예매가 없습니다]" + Color.reset);
    else System.out.println(Color.green + "[예매 취소됨] " + Reservation.cancel(String.valueOf(r.getId())) + Color.reset);
  }

  private boolean checkAdminPassword() {
    System.out.print("관리자 비밀번호 입력> ");
    return ScannerUtil.getStr().equals("1234");
  }
}
