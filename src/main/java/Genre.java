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

  // public void enrollIn(int course_id) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO enrollments (course_id, student_id, course_completion) VALUES (:courseid, :studentid, false)";
  //     con.createQuery(sql)
  //       .addParameter("courseid", course_id)
  //       .addParameter("studentid", this.getId())
  //       .executeUpdate();
  //   }
  // }
  //
  // public void passCourse(int course_id) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "UPDATE enrollments SET course_completion = true WHERE course_id = :courseid AND student_id = :studentid";
  //     con.createQuery(sql)
  //       .addParameter("courseid", course_id)
  //       .addParameter("studentid", this.getId())
  //       .executeUpdate();
  //   }
  // }
  //
  // public List<Course> getAllCourses() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT courses.id AS mId, courses.name AS mName, courses.department_id AS mDepartmentId, courses.number AS mNumber FROM courses INNER JOIN enrollments ON courses.id = enrollments.course_id WHERE enrollments.student_id = :id";
  //     List<Course> courseList = con.createQuery(sql)
  //       .addParameter("id", mId)
  //       .executeAndFetch(Course.class);
  //     return courseList;
  //   }
  // }
  //
  // public Boolean courseIsCompleted(int courseId) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT course_completion FROM enrollments WHERE course_id = :courseid AND student_id = :studentid";
  //     Boolean result = con.createQuery(sql)
  //       .addParameter("courseid", courseId)
  //       .addParameter("studentid", mId)
  //       .executeScalar(Boolean.class);
  //     return result;
  //   }
  // }
}
