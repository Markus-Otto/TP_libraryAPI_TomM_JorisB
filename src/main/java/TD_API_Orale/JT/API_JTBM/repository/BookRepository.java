package TD_API_Orale.JT.API_JTBM.repository;

import TD_API_Orale.JT.API_JTBM.dto.AuthorStatsDTO;
import TD_API_Orale.JT.API_JTBM.dto.CategoryStatsDTO;
import TD_API_Orale.JT.API_JTBM.entity.Book;
import TD_API_Orale.JT.API_JTBM.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    // Statistiques : nombre de livres par catégorie
    @Query("SELECT new TD_API_Orale.JT.API_JTBM.dto.CategoryStatsDTO(b.genre, COUNT(b)) " +
            "FROM Book b GROUP BY b.genre ORDER BY COUNT(b) DESC")
    List<CategoryStatsDTO> countBooksByCategory();

    // Statistiques : top auteurs avec le plus de livres
    @Query(value = "SELECT a.id as authorId, a.first_name as firstName, a.last_name as lastName, COUNT(b.id) as bookCount " +
            "FROM books b JOIN authors a ON b.author_id = a.id " +
            "GROUP BY a.id, a.first_name, a.last_name " +
            "ORDER BY COUNT(b.id) DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<Object[]> findTopAuthorsByBookCountNative(@Param("limit") int limit);


    // Recherche avancée avec pagination et filtres multiples
    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:authorId IS NULL OR b.author.id = :authorId) AND " +
            "(:category IS NULL OR b.genre = :category) AND " +
            "(:yearFrom IS NULL OR b.year >= :yearFrom) AND " +
            "(:yearTo IS NULL OR b.year <= :yearTo)")
    Page<Book> findBooksWithFilters(
            @Param("title") String title,
            @Param("authorId") Long authorId,
            @Param("category") Genre category,
            @Param("yearFrom") Integer yearFrom,
            @Param("yearTo") Integer yearTo,
            Pageable pageable
    );
}
