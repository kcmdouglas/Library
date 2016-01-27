import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class GenreTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Genre.all().size(), 0);
  }

  @Test
  public void genres_instatiatesName() {
    Genre genre = new Genre("Children");
    genre.save();
    assertEquals(genre.getName(), "Children");
  }

  @Test
  public void genre_deleteWorksProperly_0() {
    Genre genre = new Genre("Juvenile");
    genre.save();
    genre.delete();
    assertEquals(0, Genre.all().size());
  }

  @Test
  public void genre_updateWorksProperly() {
    Genre genre = new Genre("Juvenile");
    genre.save();
    genre.update("Children");
    assertEquals(genre.getName(), "Children");
  }

  @Test
  public void equals_returnsTrueIfSameFirstNameAndLastName() {
    Genre firstGenre = new Genre("Juvenile");
    firstGenre.save();
    Genre secondGenre = new Genre("Juvenile");
    secondGenre.save();
    assertTrue(firstGenre.equals(secondGenre));
  }

}
