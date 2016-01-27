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

  @Test
  public void getAllBooks_ListsAllBooksRelatedToGenre() {
    Genre genre = new Genre("Classics");
    genre.save();
    Book firstBook = new Book("Peter Rabbit", "Children");
    firstBook.save();
    Book secondBook = new Book("Crime and Punishment",
    "Adult");
    secondBook.save();
    Book thirdBook = new Book("Lord of the Rings", "Juvenile");
    thirdBook.save();
    firstBook.addGenre(genre.getId());
    secondBook.addGenre(genre.getId());
    thirdBook.addGenre(genre.getId());
    Book[] books = new Book[] {firstBook, secondBook, thirdBook};
    assertTrue(genre.getAllBooks().containsAll(Arrays.asList(books)));
  }

}
