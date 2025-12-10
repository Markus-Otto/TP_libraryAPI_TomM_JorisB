package TD_API_Orale.JT.API_JTBM.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom est obligatoire")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false)
    private String lastName;

    @Min(value = 1800, message = "L'année de naissance doit être réaliste")
    @Max(value = 2100, message = "L'année de naissance doit être réaliste")
    private Integer birthYear;

    // Si tu veux, on pourra plus tard ajouter une relation OneToMany vers Book,
    // mais pour le TP, le côté ManyToOne dans Book suffit.
}
