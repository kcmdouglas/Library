import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.time.LocalDate;

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

  public void checkout(int copyId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO checkouts (patron_id, copy_id, checkout_date, due_date) VALUES (:patronId, :copyId, TO_DATE (:checkOutDate, 'yyyy-mm-dd'), TO_DATE(:dueDate, 'yyyy-mm-dd'))";
      con.createQuery(sql)
         .addParameter("patronId", mId)
         .addParameter("copyId", copyId)
         .addParameter("checkOutDate", LocalDate.now().toString())
         .addParameter("dueDate", LocalDate.now().plusWeeks(3).toString())
         .executeUpdate();
    }
  }

  public List<Checkout> getAllCheckouts() {
    String sql = "SELECT id AS mId, patron_id AS mPatronId, copy_id AS mCopyId, checkout_date AS mCheckoutDate, due_date AS mDueDate FROM checkouts WHERE patron_id = :patronId";
    try(Connection con = DB.sql2o.open()) {
      List<Checkout> checkoutList = con.createQuery(sql)
        .addParameter("patronId", mId)
        .executeAndFetch(Checkout.class);
      return checkoutList;
    }
  }

}
