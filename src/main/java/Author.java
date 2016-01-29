import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Author {
  private int mId;
  private String mLastName;
  private String mFirstName;

  public int getId() {
    return mId;
  }

  public String getLastName() {
    return mLastName;
  }

  public String getFirstName() {
    return mFirstName;
  }

  public String getFullName() {
    return mLastName + ", " + mFirstName;
  }

  public Author(String lastName, String firstName) {
    this.mLastName = lastName;
    this.mFirstName = firstName;
  }

  @Override
  public boolean equals(Object otherAuthor) {
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getLastName().equals(newAuthor.getLastName()) &&
             this.getFirstName().equals(newAuthor.getFirstName());
    }
  }

  public static List<Author> all() {
    String sql = "SELECT id AS mId, last_name AS mLastName, first_name AS mFirstName FROM authors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO authors(last_name, first_name) VALUES (:lastName, :firstName)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
                          .addParameter("lastName", this.mLastName)
                          .addParameter("firstName", this.mFirstName)
                          .executeUpdate()
                          .getKey();
    }
  }

    public static Author find(int id) {
      String sql = "SELECT id AS mId, last_name AS mLastName, first_name AS mFirstName FROM authors WHERE id = :id";
      try(Connection con = DB.sql2o.open()) {
        Author author = con.createQuery(sql)
                           .addParameter("id", id)
                           .executeAndFetchFirst(Author.class);
      return author;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String deleteAuthorConnection = "DELETE FROM books_authors WHERE books_authors.author_id = :id;";
    con.createQuery(deleteAuthorConnection)
      .addParameter("id", mId)
      .executeUpdate();
    }

    try(Connection con = DB.sql2o.open()) {
    String deleteAuthor = "DELETE FROM author WHERE id = :id;";
    con.createQuery(deleteAuthor)
      .addParameter("id", mId)
      .executeUpdate();
    }
  }

  public void update(String newLastName, String newFirstName) {
    mLastName = newLastName;
    mFirstName = newFirstName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE authors SET last_name = :lastName, first_name = :firstName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("lastName", newLastName)
        .addParameter("firstName", newFirstName)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public List<Book> getAllBooks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT books.id AS mId, books.title AS mTitle, books.age_group AS mAgeGroup FROM books INNER JOIN books_authors ON books.id = books_authors.book_id WHERE books_authors.author_id = :id";
      List<Book> bookList = con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Book.class);
      return bookList;
    }
  }


}
