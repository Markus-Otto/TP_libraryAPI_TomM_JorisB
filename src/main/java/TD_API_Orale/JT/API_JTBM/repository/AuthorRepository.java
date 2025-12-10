package TD_API_Orale.JT.API_JTBM.repository;

import TD_API_Orale.JT.API_JTBM.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// repo pour gérer les auteurs (CRUD automatique grâce à JpaRepository)
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // fonction pour trouver un auteur par nom + prénom (utile pour éviter doublon)
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
