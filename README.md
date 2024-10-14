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



==============================================
This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/projetequipe-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- JDBC Driver - Microsoft SQL Server ([guide](https://quarkus.io/guides/datasource)): Connect to the Microsoft SQL
  Server database via JDBC
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus
  REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code
  for Hibernate ORM via the active record or the repository pattern

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
