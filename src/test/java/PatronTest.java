import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Patron.all().size(), 0);
  }

  @Test
  public void patron_instantiateWithFirstNameAndLastName() {
    Patron patron = new Patron("Marx", "Karl");
    patron.save();
    assertEquals("Marx", Patron.find(patron.getId()).getLastName());
    assertEquals("Karl", Patron.find(patron.getId()).getFirstName());
  }

  @Test
  public void delete_deleteWorksProperly_0() {
    Patron patron = new Patron("Marx", "Karl");
    patron.save();
    patron.delete();
    assertEquals(0, Patron.all().size());
  }

  @Test
  public void update_updateWorksProperly() {
    Patron patron = new Patron("Marx", "Karl");
    patron.save();
    patron.update("Engels", "Freidrich");
    assertEquals(patron.getLastName(), "Engels");
    assertEquals(patron.getFirstName(), "Freidrich");
  }

  @Test
  public void equals_returnsTrueIfSameFirstNameAndLastName() {
    Patron firstPatron = new Patron("Marx", "Karl");
    firstPatron.save();
    Patron secondPatron = new Patron("Marx", "Karl");
    secondPatron.save();
    assertTrue(firstPatron.equals(secondPatron));
  }

}
