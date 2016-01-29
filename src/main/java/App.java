import java.util.ArrayList;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/admin", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("books", Book.all());
      model.put("genres", Genre.all());
      model.put("genre", Genre.class);
      model.put("book", Book.class);
      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String title = request.queryParams("title");
      String authorFirst = request.queryParams("authorFirst");
      String authorLast = request.queryParams("authorLast");
      String ageGroup;

      if(Integer.parseInt(request.queryParams("ageGroupId")) == 1) {
        ageGroup = "Pre-school Children";
      } else if (Integer.parseInt(request.queryParams("ageGroupId")) == 2) {
        ageGroup = "K-5 Children";
      } else if (Integer.parseInt(request.queryParams("ageGroupId")) == 3) {
        ageGroup = "Young Adult";
      } else {
        ageGroup = "Adult";
      }

      Book book = new Book(title, ageGroup);
      book.save();

      Author author = new Author(authorLast, authorFirst);
      author.save();

      Copy copy = new Copy(book.getId());
      copy.save();

      book.addAuthor(author.getId());
      // book.addGenre();

      response.redirect("/admin");
      return null;
    });

    post("/books/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Book book = Book.find(Integer.parseInt(request.queryParams("bookId")));
      Genre genre = new Genre(request.queryParams("name"));
      genre.save();
      book.addGenre(genre.getId());

      response.redirect("/admin");
      return null;
    });

    get("/admin/catalog", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("genres", Genre.all());
      model.put("genre", Genre.class);
      model.put("authors", Author.all());
      model.put("author", Author.class);
      model.put("books", Book.all());
      model.put("book", Book.class);
      model.put("template", "templates/admin-catalog.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/admin/author/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/admin-author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/catalog", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("genres", Genre.all());
      model.put("genre", Genre.class);
      model.put("authors", Author.all());
      model.put("author", Author.class);
      model.put("books", Book.all());
      model.put("book", Book.class);
      model.put("template", "templates/catalog.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/author/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));
      model.put("genres", Genre.all());
      model.put("genre", Genre.class);
      model.put("authors", Author.all());
      model.put("author", author);
      model.put("books", Book.all());
      model.put("book", Book.class);
      model.put("template", "templates/author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/catalog/search", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/search-results.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());




  }
}
