package sullivan.gareth.library.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToMany
    private List<Book> books;

    public Author() {}

    public Author(String firstName, String lastName, List<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) &&
                Objects.equals(firstName, author.firstName) &&
                Objects.equals(lastName, author.lastName) &&
                Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, books);
    }
}
