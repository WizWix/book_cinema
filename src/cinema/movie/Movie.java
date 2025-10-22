package cinema.movie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;

public class Movie {
  private static final File file = new File("C:\\Cinema\\movie.txt");

  public long id;
  public String title;
  public String genre;

  public Movie(long id, String title, String genre) {
    this(title, genre);
    this.id = id;
  }

  public Movie(String title, String genre) {
    this.id = Instant.now().getEpochSecond();
    this.title = title;
    this.genre = genre;
  }

  public static Movie delete(String movieIdStr) {
    Movie mv = null;
    ArrayList<Movie> mvs = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String s;
      while ((s = br.readLine()) != null) {
        String[] arr = s.split(",");
        if (arr.length == 3) {
          long id = Long.parseLong(arr[0]);
          String title = arr[1];
          String genre = arr[2];
          mvs.add(new Movie(id, title, genre));
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    Iterator<Movie> iter = mvs.iterator();
    while (iter.hasNext()) {
      var m = iter.next();
      if (String.valueOf(m.id).equals(movieIdStr)) {
        mv = m;
        iter.remove();
        break;
      }
    }

    try (FileWriter fw = new FileWriter(file, false)) {
      StringBuilder sb = new StringBuilder();
      for (Movie m : mvs) sb.append(m.toFileString()).append(System.lineSeparator());
      fw.write(sb.toString());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    return mv;
  }

  private String toFileString() { return "%s,%s,%s".formatted(id, title, genre); }

  public static Movie findById(String movieIdStr) {
    var mvs = findAll();
    for (Movie m : mvs) if (movieIdStr.equals(String.valueOf(m.id))) return m;
    return null;
  }

  public static ArrayList<Movie> findAll() {
    if (file.exists()) {
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String s;
        ArrayList<Movie> mvs = new ArrayList<>();
        while ((s = br.readLine()) != null) {
          String[] arr = s.split(",");
          if (arr.length == 3) {
            long id = Long.parseLong(arr[0]);
            String title = arr[1];
            String genre = arr[2];
            mvs.add(new Movie(id, title, genre));
          }
        }
        return mvs;
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
    return new ArrayList<>();
  }

  @Override
  public String toString() { return "[%d]: %s(%s)".formatted(id, title, genre); }

  public boolean save() {
    try {
      var parent = file.getParentFile();
      if (parent != null && !parent.exists())
        // noinspection ResultOfMethodCallIgnored
        parent.mkdirs();
      try (FileWriter fw = new FileWriter(file, true)) {
        fw.write(this.toFileString() + System.lineSeparator());
        return true;
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    return false;
  }

  public String getTitle() { return title; }
}
