package cinema.util;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScannerUtil {
  private static final Scanner sc = new Scanner(System.in);

  private ScannerUtil() {}

  public static int getInt() {
    try {
      return Integer.parseInt(sc.nextLine().strip());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public static long getLong() {
    try {
      return Long.parseLong(sc.nextLine().strip());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public static char getChar() {
    String s = getStr();
    if (!s.isEmpty()) return s.charAt(0);
    else return 0;
  }

  public static String getStr() {
    try {
      return sc.nextLine().strip();
    } catch (NoSuchElementException e) {
      return "";
    }
  }

  public static double getDouble() {
    try {
      return Double.parseDouble(sc.nextLine().strip());
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }
}
