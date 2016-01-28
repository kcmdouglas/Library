import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.sql2o.*;

public class Checkout {

  private int mId;
  private int mPatronId;
  private int mCopyId;
  private String mCheckoutDate;
  private String mDueDate;

  public int getId() {
    return mId;
  }

  public int getPatronId() {
    return mPatronId;
  }

  public int getCopyId() {
    return mCopyId;
  }

  public String getCheckoutDate() {
    return mCheckoutDate;
  }

  public String getDueDate() {
    return mDueDate;
  }

  public Checkout(String checkoutDate, String dueDate) {
    this.mCheckoutDate = checkoutDate;
    this.mDueDate = dueDate;
  }

  @Override
  public boolean equals(Object otherCheckout) {
    if (!(otherCheckout instanceof Checkout)) {
      return false;
    } else {
      Checkout newCheckout = (Checkout) otherCheckout;
      return this.getCheckoutDate().equals(newCheckout.getCheckoutDate()) &&
             this.getDueDate().equals(newCheckout.getDueDate());
    }
  }

  public static List<Checkout> all() {
    String sql = "SELECT id AS mId, patron_id AS mPatronId, copy_id AS mCopyId, checkout_date AS mCheckoutDate, due_date AS mDueDate FROM checkouts";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Checkout.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO checkouts(checkout_date, due_date) VALUES (TO_DATE (:checkoutDate, 'yyyy-mm-dd'), TO_DATE (:dueDate, 'yyyy-mm-dd'))";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
          .addParameter("checkoutDate", this.mCheckoutDate)
          .addParameter("dueDate", this.mDueDate)
          .executeUpdate()
          .getKey();
    }
  }

  public static Checkout find(int id) {
      String sql = "SELECT id AS mId, patron_id AS mPatronId, copy_id AS mCopyId, checkout_date AS mCheckoutDate, due_date AS mDueDate FROM checkouts WHERE id = :id";
      try(Connection con = DB.sql2o.open()) {
        Checkout checkout = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Checkout.class);
      return checkout;
    }
  }
//We made an instance of a Checkout using a date, and here we are updating that instance with the Patron ID and the Copy ID
  public void setPatronIdCopyId(int patronId, int copyId) {
    this.mPatronId = patronId;
    this.mCopyId = copyId;
    String sql = "UPDATE checkouts SET patron_id = :patronId, copy_id = :copyId WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
         .addParameter("patronId", patronId)
         .addParameter("copyId", copyId)
         .addParameter("id", mId)
         .executeUpdate();
    }
  }

}