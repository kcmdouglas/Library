import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void books_instantiateWithTitleAndAgeGroup() {
    Book book = new Book("The Communist Manifesto", "All Ages");
    book.save();
    assertEquals("The Communist Manifesto", Book.find(book.getId()).getTitle());
    assertEquals("All Ages", Book.find(book.getId()).getAgeGroup());
  }

  @Test
  public void book_deleteWorksProperly_0() {
    Book book = new Book("Peter Rabbit", "Children");
    book.save();
    book.delete();
    assertEquals(0, Book.all().size());
  }

  @Test
  public void book_updateWorksProperly() {
    Book book = new Book("Peter Rabbit", "Children");
    book.save();
    book.update("The House At Pooh Corner", "Children");
    assertEquals(book.getTitle(), "The House At Pooh Corner");
    assertEquals(book.getAgeGroup(), "Children");
  }

  @Test
  public void equals_returnsTrueIfSameTitleAndAgeGroup() {
    Book firstBook = new Book("Peter Rabbit", "Children");
    firstBook.save();
    Book secondBook = new Book("Peter Rabbit", "Children");
    secondBook.save();
    assertTrue(firstBook.equals(secondBook));
  }

  @Test
  public void addAuthor_addsAuthorToBooksAuthorsTable() {
    Book book = new Book("Peter Rabbit", "Children");
    book.save();
    Author firstAuthor = new Author("Potter", "Beatrix");
    firstAuthor.save();
    Author secondAuthor = new Author("Inspiration", "Inherent");
    secondAuthor.save();
    book.addAuthor(firstAuthor.getId());
    book.addAuthor(secondAuthor.getId());
    Author[] authors = new Author[] {firstAuthor, secondAuthor};
    assertTrue(book.getAllAuthors().containsAll(Arrays.asList(authors)));
  }

  @Test
  public void addGenre_addsGenreToBooksGenresTable() {
    Book book = new Book("Peter Rabbit", "Children");
    book.save();
    Genre firstGenre = new Genre("Classics");
    firstGenre.save();
    Genre secondGenre = new Genre("Picture");
    secondGenre.save();
    book.addGenre(firstGenre.getId());
    book.addGenre(secondGenre.getId());
    Genre[] genres = new Genre[] {firstGenre, secondGenre};
    assertTrue(book.getAllGenres().containsAll(Arrays.asList(genres)));
  }

  @Test
  public void getAllCopies_addsCopyOfBookToCopiesTable() {
    Book book = new Book("Peter Rabbit", "Children");
    book.save();
    Copy copyOne = new Copy(book.getId());
    copyOne.save();
    Copy copyTwo = new Copy(book.getId());
    copyTwo.save();
    Copy[] copies = new Copy[] {copyOne, copyTwo};
    assertEquals(book.getAllCopies().size(), 2);
    assertEquals(book.getAllCopies().get(0).getIsAvailable(), true);

  }

}
