package TD_API_Orale.JT.API_JTBM.service;

import TD_API_Orale.JT.API_JTBM.dto.AuthorStatsDTO;
import TD_API_Orale.JT.API_JTBM.dto.CategoryStatsDTO;
import TD_API_Orale.JT.API_JTBM.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final BookRepository bookRepository;

    // Statistiques : nombre de livres par catégorie
    public List<CategoryStatsDTO> getBooksPerCategory() {
        return bookRepository.countBooksByCategory();
    }

    // Statistiques : top auteurs avec le plus de livres
    public List<AuthorStatsDTO> getTopAuthors(int limit) {
        List<Object[]> results = bookRepository.findTopAuthorsByBookCountNative(limit);

        return results.stream()
                .map(row -> new AuthorStatsDTO(
                        ((Number) row[0]).longValue(),  // authorId
                        (String) row[1],                // firstName
                        (String) row[2],                // lastName
                        ((Number) row[3]).longValue()   // bookCount
                ))
                .collect(Collectors.toList());
    }
}
