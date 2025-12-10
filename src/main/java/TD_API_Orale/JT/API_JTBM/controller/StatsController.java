package TD_API_Orale.JT.API_JTBM.controller;

import TD_API_Orale.JT.API_JTBM.dto.AuthorStatsDTO;
import TD_API_Orale.JT.API_JTBM.dto.CategoryStatsDTO;
import TD_API_Orale.JT.API_JTBM.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    // Nombre de livres par catégorie
    @GetMapping("/books-per-category")
    public List<CategoryStatsDTO> getBooksPerCategory() {
        return statsService.getBooksPerCategory();
    }

    // Top auteurs avec le plus de livres
    @GetMapping("/top-authors")
    public List<AuthorStatsDTO> getTopAuthors(@RequestParam(defaultValue = "3") int limit) {
        return statsService.getTopAuthors(limit);
    }
}
