import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.sql2o.*;
import java.time.LocalDate;

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

  public static List<Checkout> all() {
    String sql = "SELECT id AS mId, patron_id AS mPatronId, copy_id AS mCopyId, checkout_date AS mCheckoutDate, due_date AS mDueDate FROM checkouts";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Checkout.class);
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


}
