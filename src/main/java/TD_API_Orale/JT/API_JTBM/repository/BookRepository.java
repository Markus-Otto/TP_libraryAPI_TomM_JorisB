package TD_API_Orale.JT.API_JTBM.repository;

import TD_API_Orale.JT.API_JTBM.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// repo pour gérer les livres (CRUD auto)
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // fonction pour vérifier si un ISBN existe déjà (création)
    boolean existsByIsbn(String isbn);

    // fonction pour vérifier si un ISBN existe pour un autre livre (modif)
    boolean existsByIsbnAndIdNot(String isbn, Long id);

    // fonction pour filtrer les livres par auteur
    List<Book> findByAuthorId(Long authorId);

    // fonction pour filtrer les livres selon l'année
    List<Book> findByYear(Integer year);
}
