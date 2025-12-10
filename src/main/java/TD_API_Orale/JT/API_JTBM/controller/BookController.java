package TD_API_Orale.JT.API_JTBM.controller;

import TD_API_Orale.JT.API_JTBM.entity.Book;
import TD_API_Orale.JT.API_JTBM.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller pour gérer les endpoints livres
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // liste tous les livres (filtre auteur ou année si paramètres présents)
    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Integer year
    ) {
        return bookService.getAllBooks(authorId, year);
    }

    // récupère un livre par id
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // crée un livre
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book created = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // met à jour un livre
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    // supprime un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
