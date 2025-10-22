package cinema.reserve;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;

public class Reservation {
  private static final File file = new File("C:\\Cinema\\reservation.txt");
  private final long movieId;
  private final String movieTitle;
  private final String seatName;
  private long id;

  public Reservation(long id, long movieId, String movieTitle, String seatName) {
    this(movieId, movieTitle, seatName);
    this.id = id;
  }

  public Reservation(long movieId, String movieTitle, String seatName) {
    this.id = Instant.now().toEpochMilli();
    this.movieId = movieId;
    this.movieTitle = movieTitle;
    this.seatName = seatName;
  }

  public static Reservation findById(String reservationId) {
    var rvs = findAll();
    for (Reservation r : rvs) if (reservationId.equals(String.valueOf(r.id))) return r;
    return null;
  }

  public static ArrayList<Reservation> findAll() {
    if (file.exists()) {
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String s;
        ArrayList<Reservation> rvs = new ArrayList<>();
        while ((s = br.readLine()) != null) {
          String[] arr = s.split(",");
          if (arr.length == 4) {
            long id = Long.parseLong(arr[0]);
            long movieId = Long.parseLong(arr[1]);
            String movieTitle = arr[2];
            String seatName = arr[3];
            rvs.add(new Reservation(id, movieId, movieTitle, seatName));
          }
        }
        return rvs;
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
    return new ArrayList<>();
  }

  public static Reservation cancel(String reservationId) {
    Reservation rv = null;
    ArrayList<Reservation> rvs = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String s;
      while ((s = br.readLine()) != null) {
        String[] arr = s.split(",");
        if (arr.length == 4) {
          long id = Long.parseLong(arr[0]);
          long movieId = Long.parseLong(arr[1]);
          String movieTitle = arr[2];
          String seatName = arr[3];
          rvs.add(new Reservation(id, movieId, movieTitle, seatName));
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    Iterator<Reservation> iter = rvs.iterator();
    while (iter.hasNext()) {
      var r = iter.next();
      if (String.valueOf(r.id).equals(reservationId)) {
        rv = r;
        iter.remove();
        break;
      }
    }

    try (FileWriter fw = new FileWriter(file, false)) {
      StringBuilder sb = new StringBuilder();
      for (Reservation r : rvs) sb.append(r.toFileString()).append(System.lineSeparator());
      fw.write(sb.toString());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    return rv;
  }

  private String toFileString() { return "%s,%s,%s,%s".formatted(id, movieId, movieTitle, seatName); }

  public static ArrayList<Reservation> findByMovieId(String movieIdStr) {
    var rvs = findAll();
    ArrayList<Reservation> rs = new ArrayList<>();
    for (Reservation r : rvs) if (movieIdStr.equals(String.valueOf(r.movieId))) rs.add(r);
    return rs;
  }

  @Override
  public String toString() { return "영화: %s, 좌석: %s".formatted(movieTitle, seatName); }

  public String getSeatName() { return seatName; }

  public void save() {
    try (FileWriter fw = new FileWriter(file, true)) {
      fw.write(this.toFileString() + System.lineSeparator());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  public long getId() { return id; }
}
