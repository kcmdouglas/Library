import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Patron {
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
    return mFirstName + " " + mLastName;
  }

  public Patron(String lastName, String firstName) {
    this.mLastName = lastName;
    this.mFirstName = firstName;
  }

  @Override
  public boolean equals(Object otherPatron) {
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getLastName().equals(newPatron.getLastName()) &&
             this.getFirstName().equals(newPatron.getFirstName());
    }
  }

  public static List<Patron> all() {
    String sql = "SELECT id AS mId, last_name AS mLastName, first_name AS mFirstName FROM patrons";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patron.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO patrons(last_name, first_name) VALUES (:lastName, :firstName)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
                          .addParameter("lastName", this.mLastName)
                          .addParameter("firstName", this.mFirstName)
                          .executeUpdate()
                          .getKey();
    }
  }

    public static Patron find(int id) {
      String sql = "SELECT id AS mId, last_name AS mLastName, first_name AS mFirstName FROM patrons WHERE id = :id";
      try(Connection con = DB.sql2o.open()) {
        Patron patron = con.createQuery(sql)
                           .addParameter("id", id)
                           .executeAndFetchFirst(Patron.class);
      return patron;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String delete = "DELETE FROM patrons WHERE id = :id";
      con.createQuery(delete)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void update(String newLastName, String newFirstName) {
    mLastName = newLastName;
    mFirstName = newFirstName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patrons SET last_name = :lastName, first_name = :firstName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("lastName", newLastName)
        .addParameter("firstName", newFirstName)
        .addParameter("id", mId)
        .executeUpdate();
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