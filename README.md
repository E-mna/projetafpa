# projetequipe
=================== @Emna ===================

un système d'inscription avec des fonctionnalités de base comme la création de compte, 
la génération d'un token JWT, 
et des mesures de sécurité comme le hachage du mot de passe. 

1- Créer le modèle User:
Ce modèle va représenter les utilisateurs dans ta base de données.  
Ce modèle utilise PanacheEntity, qui simplifie la gestion des entités avec Quarkus.

2- Créer le DTO UserDto:
Nous avons besoin d'un Data Transfer Object (DTO) pour transférer les données lors de l'inscription.
Ce DTO sera utilisé pour recevoir les données de l'utilisateur depuis une requête HTTP.

3- Créer le service AuthService pour gérer l'inscription:
Le service AuthService gérera l'inscription d'un utilisateur. Il va :
Vérifier si l'utilisateur existe déjà.
Hacher le mot de passe pour la sécurité.
Créer un nouvel utilisateur dans la base de données.
Générer un token JWT pour cet utilisateur.
[Explication du métier :
On vérifie si l'email ou le login existent déjà dans la base de données. Si c'est le cas, une réponse de conflit est envoyée.
Si tout est bon, on crée un nouvel utilisateur avec les informations fournies.
Le mot de passe est haché en utilisant BCrypt pour des raisons de sécurité.
Un token JWT est généré pour l'utilisateur une fois inscrit.]

4- méthode pour générer le Token JWT
JwtUtil pour gérer la génération et la validation du token.
La méthode generateToken génère un token JWT qui contient le login de l'utilisateur et son rôle, ainsi qu'une date d'expiration.
Le token est signé avec une clé secrète via l'algorithme HS256.

5- Gérer la vérification du token
validateToken():méthode peut être utilisée dans des endpoints protégés, comme pour l'accès à des ressources qui nécessitent d'être connecté.

6-
Endpoint sécurisé:
Ce token peut ensuite être utilisé pour sécuriser d'autres endpoints via des vérifications basées sur les rôles.

------------------------------------------------
7- Modification du mot de passe de l'utilisateur 
 
Fonctionnalité : Un utilisateur authentifié peut modifier son mot de passe.
L'utilisateur doit fournir son ancien mot de passe et un nouveau mot de passe dans la requête.
Le token JWT est extrait pour identifier l'utilisateur actuel.
L'ancien mot de passe est vérifié via BCrypt.
Si l'ancien mot de passe est correct, le nouveau mot de passe est haché avec BCrypt et mis à jour dans la base de données.
Si le mot de passe est modifié avec succès, un message de confirmation est retourné.
Si l'ancien mot de passe est incorrect, une erreur 400 Bad Request est retournée.

-----------------------------------------------------------
8. Modifier les coordonnées de l'utilisateur (login, email)
-----------------------------------------------------------
 
Fonctionnalité : Un utilisateur authentifié peut modifier son login et son email.
L'utilisateur doit fournir le nouveau login et email.
Le token JWT est utilisé pour identifier l'utilisateur actuel.
L'API vérifie que le nouvel email et le nouveau login ne sont pas déjà utilisés par d'autres utilisateurs.
Si les informations sont uniques et valides, elles sont mises à jour dans la base de données.
Si l'email ou le login sont déjà utilisés, l'API retourne une erreur 409 Conflict.
En résumé, ce que peut faire ton API maintenant :
Inscription d'utilisateurs avec un mot de passe sécurisé et génération d'un token JWT.
Authentification via token JWT et accès à des endpoints protégés.
Modification du mot de passe pour les utilisateurs authentifiés, avec vérification du mot de passe actuel.
Modification des coordonnées (email et login) avec validation de leur unicité.
Ces fonctionnalités sont protégées par des rôles (USER, ADMIN) et utilisent le token JWT pour l'authentification.
 
