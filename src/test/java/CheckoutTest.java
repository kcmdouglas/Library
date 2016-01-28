import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class CheckoutTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Checkout.all().size(), 0);
  }


  @Test
  public void find_findsInstanceOfCheckout() {
    Patron patron = new Patron("Baggins", "Bilbo");
    patron.save();
    Book book = new Book("There And Back Again", "Adult");
    Copy copy = new Copy(book.getId());
    book.save();
    copy.save();
    patron.checkout(copy.getId());
    Checkout newCheckout = Checkout.all().get(0);
    assertEquals(newCheckout.find(newCheckout.getId()), Checkout.all().size());
  }
}
