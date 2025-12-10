package TD_API_Orale.JT.API_JTBM.service;

import TD_API_Orale.JT.API_JTBM.entity.Author;
import TD_API_Orale.JT.API_JTBM.entity.Book;
import TD_API_Orale.JT.API_JTBM.entity.Genre;
import TD_API_Orale.JT.API_JTBM.exception.ResourceNotFoundException;
import TD_API_Orale.JT.API_JTBM.repository.AuthorRepository;
import TD_API_Orale.JT.API_JTBM.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// service pour gérer la logique métier des livres
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    // Recherche paginée avec filtres
    public Page<Book> getAllBooksWithFilters(
            String title,
            Long authorId,
            Genre category,
            Integer yearFrom,
            Integer yearTo,
            Pageable pageable
    ) {
        return bookRepository.findBooksWithFilters(title, authorId, category, yearFrom, yearTo, pageable);
    }

    // Ancienne méthode (on peut la garder pour compatibilité ou la supprimer)
    public List<Book> getAllBooks(Long authorId, Integer year) {
        // si je veux filtrer par auteur
        if (authorId != null) {
            return bookRepository.findByAuthorId(authorId);
        }

        // si je veux filtrer par année
        if (year != null) {
            return bookRepository.findByYear(year);
        }

        // sinon je renvoie tout
        return bookRepository.findAll();
    }

    // récupère un livre par id
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livre introuvable avec id = " + id));
    }

    // crée un livre
    public Book createBook(Book book) {

        // Vérifie que l'ISBN est unique
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IllegalArgumentException("ISBN déjà utilisé : " + book.getIsbn());
        }

        // Vérifie que l'auteur existe bien en base
        Long authorId = book.getAuthor().getId();
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Auteur introuvable avec id = " + authorId));

        // Rattache l'auteur chargé à partir de la BD
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    // Mets à jour un livre
    public Book updateBook(Long id, Book updatedBook) {
        Book existing = getBookById(id); // je récupère le livre ou exception

        // Vérifie unicité de l'ISBN (hors ce livre-ci)
        if (bookRepository.existsByIsbnAndIdNot(updatedBook.getIsbn(), id)) {
            throw new IllegalArgumentException("ISBN déjà utilisé par un autre livre : " + updatedBook.getIsbn());
        }

        // Vérifie l'auteur
        Long authorId = updatedBook.getAuthor().getId();
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Auteur introuvable avec id = " + authorId));

        // Mets à jour les champs
        existing.setTitle(updatedBook.getTitle());
        existing.setIsbn(updatedBook.getIsbn());
        existing.setYear(updatedBook.getYear());
        existing.setGenre(updatedBook.getGenre());
        existing.setAuthor(author);

        return bookRepository.save(existing);
    }

    // supprime un livre
    public void deleteBook(Long id) {
        Book existing = getBookById(id); // je vérifie qu'il existe
        bookRepository.delete(existing);
    }
}
