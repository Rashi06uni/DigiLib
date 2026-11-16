
package com.example.digitalib;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private final ArrayList<Book> books = new ArrayList<>();

    public BookRepository() {
        initializeBooks();
    }

    private void initializeBooks() {

        books.add(new Book(
                "B101", "Learn Java", "Herbert Schildt",
                "Programming",
                "Learn the fundamentals of Java programming.",
                true
        ));

        books.add(new Book(
                "B102", "Python Basics", "Eric Matthes",
                "Programming",
                "An introduction to Python programming.",
                true
        ));

        books.add(new Book(
                "B103", "The Alchemist", "Paulo Coelho",
                "Fiction",
                "A journey about dreams and destiny.",
                true
        ));

        books.add(new Book(
                "B104", "A Brief History of Time", "Stephen Hawking",
                "Science",
                "Explore space, time and the universe.",
                true
        ));

        books.add(new Book(
                "B105", "Think and Grow Rich", "Napoleon Hill",
                "Business",
                "Learn principles of success and achievement.",
                true
        ));

        books.add(new Book(
                "B106", "C++ Fundamentals", "Bjarne Stroustrup",
                "Programming",
                "Understand the core concepts of C++.",
                true
        ));

        books.add(new Book(
                "B107", "The Hobbit", "J.R.R. Tolkien",
                "Fiction",
                "A fantasy adventure about a hobbit and a dragon.",
                true
        ));

        books.add(new Book(
                "B108", "Cosmos", "Carl Sagan",
                "Science",
                "Discover astronomy and the wonders of the universe.",
                true
        ));

        books.add(new Book(
                "B109", "Atomic Habits", "James Clear",
                "Business",
                "Build good habits and improve every day.",
                true
        ));

        books.add(new Book(
                "B110", "Clean Code", "Robert C. Martin",
                "Programming",
                "Learn to write clean and maintainable code.",
                true
        ));
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Book getBookById(String id) {

        if (id == null) return null;

        for (Book book : books) {
            if (book.getId().equalsIgnoreCase(id.trim())) {
                return book;
            }
        }

        return null;
    }

    public List<Book> getBorrowedBooks() {

        ArrayList<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (!book.isAvailable()) {
                result.add(book);
            }
        }

        return result;
    }

    public boolean borrowBook(String id) {

        Book book = getBookById(id);

        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            return true;
        }

        return false;
    }

    public boolean returnBook(String id) {

        Book book = getBookById(id);

        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            return true;
        }

        return false;
    }
}
