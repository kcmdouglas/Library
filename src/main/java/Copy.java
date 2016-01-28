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

  public Copy(int bookId) {
    this.mBookId = bookId;
  }

  public void save() {
    String sql = "INSERT INTO copies (book_id, is_available) VALUES (:bookId, true)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
          .addParameter("bookId", this.mBookId)
          .executeUpdate()
          .getKey();
    }
  }


}
