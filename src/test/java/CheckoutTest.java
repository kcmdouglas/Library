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
  public void checkout_instantiateWithCheckoutDateAndDueDate() {
    Checkout checkout = new Checkout("2016-01-27", "2016-02-15");
    checkout.save();
    assertEquals("2016-01-27", Checkout.find(checkout.getId()).getCheckoutDate());
    assertEquals("2016-02-15", Checkout.find(checkout.getId()).getDueDate());
  }

  @Test
  public void equals_returnsTrueIfSameTitleAndAgeGroup() {
    Checkout firstCheckout = new Checkout("2016-01-27", "2016-02-15");
    firstCheckout.save();
    Checkout secondCheckout = new Checkout("2016-01-27", "2016-02-15");
    secondCheckout.save();
    assertTrue(firstCheckout.equals(secondCheckout));
  }

}
