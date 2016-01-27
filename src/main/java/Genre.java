import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Genre {
  private int mId;
  private String mName;

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public Genre(String name) {
    this.mName = name;
  }

  @Override
  public boolean equals(Object otherGenre) {
    if (!(otherGenre instanceof Genre)) {
      return false;
    } else {
      Genre newGenre = (Genre) otherGenre;
      return this.getName().equals(newGenre.getName());
    }
  }

  public static List<Genre> all() {
    String sql = "SELECT id AS mId, name AS mName FROM genres";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Genre.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO genres(name) VALUES (:name)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
                          .addParameter("name", this.mName)
                          .executeUpdate()
                          .getKey();
    }
  }

    public static Genre find(int id) {
      String sql = "SELECT id AS mId, name AS mName FROM genres WHERE id = :id";
      try(Connection con = DB.sql2o.open()) {
        Genre genre = con.createQuery(sql)
                           .addParameter("id", id)
                           .executeAndFetchFirst(Genre.class);
      return genre;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String delete = "DELETE FROM genres WHERE id = :id";
      con.createQuery(delete)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void update(String name) {
    mName = name;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE genres SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public List<Book> getAllBooks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT books.id AS mId, books.title AS mTitle, books.age_group AS mAgeGroup FROM books INNER JOIN books_genres ON books.id = books_genres.book_id WHERE books_genres.genre_id = :id";
      List<Book> bookList = con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Book.class);
      return bookList;
    }
  }


}
