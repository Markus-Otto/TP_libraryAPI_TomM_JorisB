package TD_API_Orale.JT.API_JTBM.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "books",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_book_isbn", columnNames = "isbn")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "L'ISBN est obligatoire")
    @Pattern(regexp = "^(?:\\d{10}|\\d{13}|\\d{1,5}-\\d{1,7}-\\d{1,7}-[\\dX])$",
            message = "Le format de l'ISBN est invalide (ISBN-10 ou ISBN-13)")
    @Column(nullable = false, unique = true, length = 50)
    private String isbn;

    @NotNull(message = "L'année de publication est obligatoire")
    @Min(value = 1450, message = "L'année doit être supérieure ou égale à 1450")
    @Max(value = 2025, message = "L'année ne peut pas dépasser 2025")
    private Integer year;

    @NotNull(message = "Le genre est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Genre genre;

    @NotNull(message = "L'auteur est obligatoire")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
