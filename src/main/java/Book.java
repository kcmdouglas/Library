import java.util.ArrayList;
import java.util.List;
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
  //
  // public void setEnrollmentDate(String enrollment) {
  //   mEnrollmentDate = enrollment;
  //   String sql = "UPDATE students SET enrollment_date = to_date(:enrollment, 'YYYY-MM-DD') WHERE id = :id";
  //   try(Connection con = DB.sql2o.open()) {
  //     con.createQuery(sql)
  //       .addParameter("enrollment", enrollment)
  //       .addParameter("id", mId)
  //       .executeUpdate();
  //   }
  // }
  //
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

  // public void setMajor(int department) {
  //   this.mDepartmentId = department;
  //   String sql = "UPDATE students SET department_id = :departmentId WHERE id = :id";
  //   try(Connection con = DB.sql2o.open()) {
  //     con.createQuery(sql)
  //       .addParameter("departmentId", department)
  //       .addParameter("id", mId)
  //       .executeUpdate();
  //   }
  // }
  //
    public static Book find(int id) {
      String sql = "SELECT id AS mId, title AS mTitle, age_group AS mAgeGroup FROM books WHERE id = :id";
      try(Connection con = DB.sql2o.open()) {
        Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }
  //
  // public void delete() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String deleteRelationships = "DELETE FROM enrollments WHERE student_id = :id";
  //     con.createQuery(deleteRelationships)
  //       .addParameter("id", mId)
  //       .executeUpdate();
  //   }
  //
  //   try(Connection con = DB.sql2o.open()) {
  //   String deleteStudent = "DELETE FROM students WHERE id = :id;";
  //   con.createQuery(deleteStudent)
  //     .addParameter("id", mId)
  //     .executeUpdate();
  //   }
  // }
  //
  // public void update(String newName) {
  //   mName = newName;
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "UPDATE students SET name = :name WHERE id = :id";
  //     con.createQuery(sql)
  //       .addParameter("name", newName)
  //       .addParameter("id", mId)
  //       .executeUpdate();
  //   }
  // }
  //
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
