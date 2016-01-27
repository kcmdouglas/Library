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
  //
  // @Test
  // public void course_deleteWorksProperly_0() {
  //   Course course = new Course(1, 101, "Programming and You");
  //   course.save();
  //   course.delete();
  //   assertEquals(0, Course.all().size());
  // }
  //
  // @Test
  // public void course_updateWorksProperly() {
  //   Course course = new Course(1, 101, "Programming and You");
  //   course.save();
  //   course.update(2, 201, "World War I");
  //   assertEquals(course.getName(), "World War I");
  //   assertEquals(course.getNumber(), 201);
  //   assertEquals(course.getDepartmentId(), 2);
  // }
  //
  // @Test
  // public void equals_returnsTrueIfSameNameAndAbbreviation() {
  //   Course firstCourse = new Course(1, 101, "Programming and You");
  //   firstCourse.save();
  //   Course secondCourse = new Course(1, 101, "Programming and You");
  //   secondCourse.save();
  //   assertTrue(firstCourse.equals(secondCourse));
  // }
}
