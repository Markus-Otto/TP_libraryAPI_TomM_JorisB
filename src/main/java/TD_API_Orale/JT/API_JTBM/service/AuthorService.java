package TD_API_Orale.JT.API_JTBM.service;

import TD_API_Orale.JT.API_JTBM.entity.Author;
import TD_API_Orale.JT.API_JTBM.exception.ResourceNotFoundException;
import TD_API_Orale.JT.API_JTBM.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// service pour gérer la logique métier des auteurs
@Service
@RequiredArgsConstructor // j'utilise lombok pour générer le constructeur
public class AuthorService {

    private final AuthorRepository authorRepository;

    // je récupère tous les auteurs
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // je récupère un auteur par id ou je lance une exception si introuvable
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auteur introuvable avec id = " + id));
    }

    // je crée un auteur (je peux vérifier les doublons si je veux)
    public Author createAuthor(Author author) {

        // si un auteur existe déjà avec même prénom + nom, je peux refuser
        if (authorRepository.existsByFirstNameAndLastName(author.getFirstName(), author.getLastName())) {
            // ici je fais simple, je pourrais créer une autre exception plus précise
            throw new IllegalArgumentException("Auteur déjà existant : " +
                    author.getFirstName() + " " + author.getLastName());
        }

        return authorRepository.save(author);
    }

    // je mets à jour un auteur existant
    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existing = getAuthorById(id); // je récupère l'auteur ou exception

        // je mets à jour les champs simples
        existing.setFirstName(updatedAuthor.getFirstName());
        existing.setLastName(updatedAuthor.getLastName());
        existing.setBirthYear(updatedAuthor.getBirthYear());

        return authorRepository.save(existing);
    }

    // je supprime un auteur
    public void deleteAuthor(Long id) {
        Author existing = getAuthorById(id); // je vérifie qu'il existe
        authorRepository.delete(existing);
    }
}
