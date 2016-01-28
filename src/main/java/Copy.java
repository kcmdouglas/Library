import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.sql2o.*;

public class Copy {

  private int mId;
  private boolean mIsAvailable;
  private int mBookId;

  public int getId() {
    return mId;
  }

  public boolean getIsAvailable() {
    return mIsAvailable;
  }

  public int getBookId() {
    return mBookId;
  }

  public Copy(int bookId, boolean isAvailable) {
    this.mBookId = bookId;
    // this.mIsAvailable = isAvailable;
    // isAvailable = true;

  }

  //Edit these to reflect changes in contrustor (i.e. inputting a book ID, isAvailable boolean--if only GitHub was up...)

  // public static List<Copy> all() {
  //   String sql = "SELECT id AS mId, book_id AS mBookId is_available AS isAvailable FROM copies";
  //   try(Connection con = DB.sql2o.open()) {
  //     return con.createQuery(sql).executeAndFetch(Copy.class);
  //   }
  // }
  //
  //
  // public void save() {
  //   String sql = "INSERT INTO copies (book_id, is_available) VALUES (:bookId, :isAvailable))";
  //   try(Connection con = DB.sql2o.open()) {
  //     this.mId = (int) con.createQuery(sql, true)
  //         .addParameter("checkoutDate", this.mCopyDate)
  //         .addParameter("dueDate", this.mDueDate)
  //         .executeUpdate()
  //         .getKey();
  //   }
  // }
  //
  // public static Copy find(int id) {
  //     String sql = "SELECT id AS mId, patron_id AS mPatronId, copy_id AS mCopyId, checkout_date AS mCopyDate, due_date AS mDueDate FROM copies WHERE id = :id";
  //     try(Connection con = DB.sql2o.open()) {
  //       Copy checkout = con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeAndFetchFirst(Copy.class);
  //     return checkout;
  //   }
  // }

}
