package cinema.movie;

import cinema.reserve.Reservation;
import cinema.util.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class Seats {
  public static final int MAX_ROW = 5;
  public static final int MAX_COL = 9;
  private final String[][] map = new String[MAX_ROW][MAX_COL];

  public Seats(ArrayList<Reservation> reservations) {
    for (String[] r : map) Arrays.fill(r, "O");
    reservations.forEach(r -> mark(r.getSeatName()));
  }

  public MarkStatus mark(String seatName) {
    String sn = seatName.toUpperCase();
    try {
      char row = (char) (sn.charAt(0) - 65); // 0 = A
      int col = Integer.parseInt(String.valueOf(sn.charAt(1))) - 1; // starts with 0
      if (map[row][col].equals("O")) {
        map[row][col] = "X";
        return new MarkStatus(true, null);
      }
      return new MarkStatus(false, "이미 예약된 좌석");
    } catch (NumberFormatException e) {
      System.err.println(e.getMessage());
      return new MarkStatus(false, "좌석 번호 해석 실패");
    } catch (ArrayIndexOutOfBoundsException e) {
      System.err.println(e.getMessage());
      return new MarkStatus(false, "잘못된 좌석 번호");
    }
  }

  public void show() {
    System.out.println("-".repeat(20));
    System.out.println(Color.purple + "    S C R E E N     " + Color.reset);
    System.out.println("-".repeat(20));
    for (int i = 0 ; i < MAX_ROW ; i++) {
      System.out.print(Color.purple + (char) (i + 65) + " " + Color.reset);
      for (int j = 0 ; j < MAX_COL ; j++) System.out.print(map[i][j] + " ");
      System.out.println();
    }
    System.out.print(Color.purple + "  1 2 3 4 5 6 7 8 9" + Color.reset);
    System.out.println();
  }

  public static class MarkStatus {
    public boolean isSuccess;
    public String reason;

    public MarkStatus(final boolean isSuccess, final String reason) {
      this.isSuccess = isSuccess;
      this.reason = reason;
    }
  }
}
