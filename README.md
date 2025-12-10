# API REST - Gestion de Bibliothèque

API RESTful développée avec Spring Boot pour gérer une bibliothèque de livres et d'auteurs.

## Technologies utilisées

- Java 25
- Spring Boot 3.5.7
- Spring Data JPA / Hibernate
- Spring Security (API Key)
- MySQL
- Maven
- Lombok

## Prérequis

- Java 25 ou supérieur
- Maven 3.6+
- MySQL 8.0+
- Un client REST (Postman, Insomnia, curl, etc.)

## Installation et lancement

### 1. Cloner le projet

```bash
git clone <url-du-repo>
cd API_JTBM
```

# Configurer la base de donnée
Créer une base de donnée MySQL :
```
CREATE DATABASE library_db;
```
Modifier le fichier src/main/resources/application.properties avec vos paramètres :
```
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=votre_username
spring.datasource.password=votre_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Clé API pour sécuriser les endpoints POST/PUT/DELETE
app.api-key=votre-cle-secrete-ici
```
#Compiler et lancer l'application
```
mvn clean install
mvn spring-boot:run
```
L'application démarre sur http://localhost:8080

Architecture du projet
Le projet suit l'architecture MVC (Model-View-Controller) :

src/main/java/TD_API_Orale/JT/API_JTBM/
```
├── controller/       # Controlleurs REST (API endpoints)
├── service/          # Logique métier
├── repository/       # Accès aux données (Spring Data JPA)
├── entity/           # Entités JPA (Author, Book, Genre)
├── dto/              # Objets de transfert (DTOs)
├── exception/        # Gestion des erreurs
└── security/         # Configuration sécurité (API Key filter)
```
Modèle de données
Author (Auteur)
```
{
  "id": 1,
  "firstName": "Victor",
  "lastName": "Hugo",
  "birthYear": 1802
}
```

Book (Livre)
```
{
  "id": 1,
  "title": "Les Misérables",
  "isbn": "978-2070409228",
  "year": 1862,
  "genre": "NOVEL",
  "author": {
    "id": 1,
    "firstName": "Victor",
    "lastName": "Hugo",
    "birthYear": 1802
  }
}
```
Genre (Enum)
- NOVEL : Roman
- ESSAY : Essai
- POETRY : Poésie
- OTHER : Autre
Endpoints disponibles
### Endpoints publics (pas de clé API requise)
Tous les endpoints GET sont accessibles sans authentification.

### Endpoints protégés
Les endpoints POST, PUT, DELETE nécessitent l'envoi d'un header :
```
X-API-KEY: votre-cle-secrete-ici
```
### Endpoints Auteurs
Lister tous les auteurs
```
GET /authors
```
Réponse :
```
[
  {
    "id": 1,
    "firstName": "Victor",
    "lastName": "Hugo",
    "birthYear": 1802
  }
]
```
Récupérer un auteur par ID
```
GET /authors/{id}
```
Exemple :
```
GET /authors/1
```
Créer un auteur
```
POST /authors
Content-Type: application/json
X-API-KEY: votre-cle-secrete-ici

{
  "firstName": "Albert",
  "lastName": "Camus",
  "birthYear": 1913
}
```
Modifier un auteur
```
PUT /authors/{id}
Content-Type: application/json
X-API-KEY: votre-cle-secrete-ici

{
  "firstName": "Albert",
  "lastName": "Camus",
  "birthYear": 1913
}
```
Supprimer un auteur
```
DELETE /authors/{id}
X-API-KEY: votre-cle-secrete-ici
```
###  Endpoints Livres
Lister tous les livres (avec pagination et filtres)
```
GET /books?page=0&size=10&sortBy=year&sortDirection=desc
```
Paramètres disponibles :

Paramètre /	Type / Description	Défaut
title /	String / Recherche partiel dans le titre /	-
authorId /	Long /	Filtrer par ID d'auteur /	-
category /	Genre /	Filtrer par catégorie (NOVEL, ESSAY, POETRY, OTHER) /	-
yearFrom /	Integer /	Année minimale de publication /	-
yearTo /	Integer /	Année maximale de publication /	-
page /	Integer /	Numéro de page (commence à 0) /	0
size /	Integer /	Nombre d'éléments par page /	10
sortBy /	String /	Champ de tri (id, title, year, etc.) /	id
sortDirection /	String /	Direction du tri (asc ou desc) /	asc
Exemples de requetes :

# Tous les livres, page 1, 10 éléments
```
GET /books
```
# Livres triés par année décroissante
```
GET /books?sortBy=year&sortDirection=desc
```
# Livres de catégorie NOVEL
```
GET /books?category=NOVEL
```
# Livres publié entre 1950 et 2000
```
GET /books?yearFrom=1950&yearTo=2000
```
# Recherche par titre
```
GET /books?title=Harry
```
# Livres d'un auteur spécifique
```
GET /books?authorId=1
```
# Combinaison de filtres
```
GET /books?category=NOVEL&yearFrom=2000&sortBy=year&sortDirection=desc&page=0&size=5
```
Réponse paginée :
```
{
  "content": [
    {
      "id": 1,
      "title": "Les Misérables",
      "isbn": "978-2070409228",
      "year": 1862,
      "genre": "NOVEL",
      "author": {
        "id": 1,
        "firstName": "Victor",
        "lastName": "Hugo",
        "birthYear": 1802
      }
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 25,
  "totalPages": 3,
  "last": false
}
```
Récupérer un livre par ID
```
GET /books/{id}
```
Créer un livre
```
POST /books
Content-Type: application/json
X-API-KEY: votre-cle-secrete-ici

{
  "title": "L'Étranger",
  "isbn": "978-2070360024",
  "year": 1942,
  "genre": "NOVEL",
  "author": {
    "id": 2
  }
}
```
Validations :

title : obligatoire, non vide
isbn : obligatoire, format ISBN-10 ou ISBN-13, unique
year : obligatoire, entre 1450 et 2025
genre : obligatoire (NOVEL, ESSAY, POETRY, OTHER)
author.id : obligatoire, doit exister en base
Modifier un livre
```
PUT /books/{id}
Content-Type: application/json
X-API-KEY: votre-cle-secrete-ici

{
  "title": "L'Étranger",
  "isbn": "978-2070360024",
  "year": 1942,
  "genre": "NOVEL",
  "author": {
    "id": 2
  }
}
```
Supprimer un livre
```
DELETE /books/{id}
X-API-KEY: votre-cle-secrete-ici
```
# Endpoints Statistiques
Nombre de livres par catégorie
GET /stats/books-per-category

Réponse :
```
[
  {
    "category": "NOVEL",
    "count": 15
  },
  {
    "category": "ESSAY",
    "count": 8
  },
  {
    "category": "POETRY",
    "count": 5
  },
  {
    "category": "OTHER",
    "count": 2
  }
]
```
Top auteurs avec le plus de livres
```
GET /stats/top-authors?limit=3
```
Paramètres :

limit : Nombre d'auteurs à retourner (défaut: 3)
Réponse :
```
[
  {
    "authorId": 1,
    "firstName": "Victor",
    "lastName": "Hugo",
    "bookCount": 12
  },
  {
    "authorId": 3,
    "firstName": "Émile",
    "lastName": "Zola",
    "bookCount": 9
  },
  {
    "authorId": 2,
    "firstName": "Albert",
    "lastName": "Camus",
    "bookCount": 7
  }
]
```
Gestion des erreurs
L'API retourne des erreurs au format JSON structuré.

Erreur de validation (400)
```
{
  "timestamp": "2025-12-10T14:30:00",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "title": "Le titre est obligatoire",
    "isbn": "Le format de l'ISBN est invalide (ISBN-10 ou ISBN-13)"
  }
}
```
Ressource introuvable (404)
```
{
  "timestamp": "2025-12-10T14:30:00",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Livre introuvable avec id = 999"
}
```
ISBN déja utilisé (400)
```
{
  "timestamp": "2025-12-10T14:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "ISBN déjà utilisé : 978-2070409228"
}
```
Clé API invalide (401)
```
HTTP/1.1 401 Unauthorized
API key invalide ou absente
```
Exemples avec cURL
Créer un auteur
```
curl -X POST http://localhost:8080/authors \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: votre-cle-secrete-ici" \
  -d '{
    "firstName": "Marcel",
    "lastName": "Proust",
    "birthYear": 1871
  }'
```
Créer un livre
```
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: votre-cle-secrete-ici" \
  -d '{
    "title": "Du côté de chez Swann",
    "isbn": "978-2070754274",
    "year": 1913,
    "genre": "NOVEL",
    "author": {
      "id": 1
    }
  }'
```
Lister les livres avec filtres
```
curl "http://localhost:8080/books?category=NOVEL&yearFrom=1900&yearTo=2000&sortBy=year&sortDirection=desc"
```
Statistiques
```
curl http://localhost:8080/stats/books-per-category
curl "http://localhost:8080/stats/top-authors?limit=5"
```
Tests avec Postman
Un export Postman est disponible pour tester l'API : API_JTBM_Postman.json
