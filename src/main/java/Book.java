import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.sql2o.*;

public class Book {
  private int mId;
  private String mTitle;
  private String mAgeGroup;


  public int getId() {
    return mId;
  }

  public String getTitle() {
    return mTitle;
  }

  public String getAgeGroup() {
    return mAgeGroup;
  }

  public Book(String title, String ageGroup) {
    this.mTitle = title;
    this.mAgeGroup = ageGroup;
  }

  @Override
  public boolean equals(Object otherBook) {
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) &&
        this.getAgeGroup().equals(newBook.getAgeGroup());
    }
  }

  public static List<Book> all() {
    String sql = "SELECT id AS mId, title AS mTitle, age_group AS mAgeGroup FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public void save() {
      String sql = "INSERT INTO books(title, age_group) VALUES (:name, :ageGroup)";
      try(Connection con = DB.sql2o.open()) {
        this.mId = (int) con.createQuery(sql, true)
          .addParameter("name", this.mTitle)
          .addParameter("ageGroup", this.mAgeGroup)
          .executeUpdate()
          .getKey();
      }
  }

    public static Book find(int id) {
      String sql = "SELECT id AS mId, title AS mTitle, age_group AS mAgeGroup FROM books WHERE id = :id";
      try(Connection con = DB.sql2o.open()) {
        Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String deleteBook = "DELETE FROM books WHERE id = :id;";
    con.createQuery(deleteBook)
      .addParameter("id", mId)
      .executeUpdate();
    }
  }

  public void update(String newTitle, String newAgeGroup) {
    mTitle = newTitle;
    mAgeGroup = newAgeGroup;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET title = :title, age_group = :ageGroup WHERE id = :id";
      con.createQuery(sql)
        .addParameter("title", newTitle)
        .addParameter("ageGroup", newAgeGroup)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void addAuthor(int author_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_authors (book_id, author_id) VALUES (:bookId, :authorId)";
      con.createQuery(sql)
        .addParameter("bookId", this.getId())
        .addParameter("authorId", author_id)
        .executeUpdate();
    }
  }

  public List<Author> getAllAuthors() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT authors.id AS mId, authors.last_name AS mLastName, authors.first_name AS mFirstName FROM authors INNER JOIN books_authors ON authors.id = books_authors.author_id WHERE books_authors.book_id = :id";
      List<Author> authorList = con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Author.class);
      return authorList;
    }
  }

  public void addGenre(int genreId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_genres (book_id, genre_id) VALUES (:bookId, :genreId)";
      con.createQuery(sql)
         .addParameter("bookId", this.getId())
         .addParameter("genreId", genreId)
         .executeUpdate();
    }
  }

  public void addCopy() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO copies (book_id, is_available) VALUES (:bookId, true)";
      con.createQuery(sql)
         .addParameter("bookId", this.getId())
         .executeUpdate();
    }
  }

  public List<Genre> getAllGenres() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT genres.id AS mId, genres.name AS mName FROM genres INNER JOIN books_genres ON genres.id = books_genres.genre_id WHERE books_genres.book_id = :id";
      List<Genre> genreList = con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Genre.class);
      return genreList;
    }
  }


}
