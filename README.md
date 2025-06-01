# EBanking Backend  

Le projet **EBanking_backend** est une application bancaire développée avec Spring Boot. Elle offre la gestion des clients, des comptes bancaires et des transactions tout en assurant une sécurité basée sur JWT. Ce backend est conçu pour répondre à diverses opérations bancaires applicables dans un environnement REST.

---

## **Structure du Projet**

L'application est organisée en plusieurs packages pour optimiser la séparation des responsabilités, comme indiqué ci-dessous :

### **1. `com.jee.ebanking_backend.entities`**
Ce package contient les entités principales utilisées pour modéliser la base de données.

- **`Customer`** : 
  Représente un client de la banque. Attributs importants : `id`, `name`, `email`. Relation `@OneToMany` avec `BankAccount`.
  
- **`BankAccount`** : 
  Classe de base pour représenter les comptes bancaires (types spécifiques : `CurrentAccount` et `SavingAccount`). Héritage basé sur une **@Inheritance(SINGLE_TABLE)** avec une **@DiscriminatorColumn** :
  - `SavingAccount` : Inclut un champ spécifique `interestRate` pour les comptes épargne.
  - `CurrentAccount` : Supporte un champ `overDraft` pour le découvert bancaire.

- **`AccountOperation`** : 
  Permet de suivre les transactions effectuées sur un compte, comme les dépôts ou les retraits. Relation `@ManyToOne` avec `BankAccount`.

- **`User`** : 
  Implémente `UserDetails` pour gérer les utilisateurs de l'application (ex. ADMIN ou USER). Contient les rôles et les informations de connexion.

---

### **2. `com.jee.ebanking_backend.repositories`**
Ce package contient les interfaces JPA pour manipuler les entités dans la base de données.

- **`CustomerRepository`** : Gère les opérations CRUD pour les objets `Customer`.
- **`BankAccountRepository`** : Fournit des méthodes de manipulation pour les comptes bancaires `BankAccount`.
- **`AccountOperationRepository`** : Accès aux informations liées aux transactions bancaires.
- **`UserRepository`** : Opérations liées aux utilisateurs (authentification et rôles).

---

### **3. `com.jee.ebanking_backend.dtos`**
Le package DTO (Data Transfer Objects) contient des classes pour transférer les données entre le client et le backend.

- **`TransferRequestDTO`** : Sert pour effectuer des transferts d'argent entre deux comptes. Attributs : `accountSource`, `accountDestination`, `amount`, `description`.
- **`UpdateAccountDTO`** : Représente les données nécessaires pour mettre à jour les informations d'un compte.

---

### **4. `com.jee.ebanking_backend.enums`**
Ce package contient des énumérations définissant certaines constantes dans l'application.

- **`AccountStatus`** : Statuts disponibles pour un compte (CREATED, ACTIVATED, SUSPENDED).
- **`OperationType`** : Types d'opérations bancaires (DEBIT, CREDIT).
- **`Role` et `Permission`** : Rôles et permissions utilisateur (ADMIN, USER).

---

### **5. `com.jee.ebanking_backend.services`**
Contient la logique métier (services) qui implémente les fonctionnalités principales de l'application.

- **`BankAccountService`** : Interface pour la gestion des comptes bancaires.
- **`BankAccountServiceImpl`** : Implémentation de `BankAccountService` pour les opérations comme création de comptes ou gestion de transactions.
- **`JWTService`** : Gère les tokens JWT pour la sécurisation des requêtes.
- **`UserService`** : Opérations liées à la gestion des utilisateurs au sein du système.

---

### **6. `com.jee.ebanking_backend.web`**
Ce package contient les contrôleurs REST qui exposent les endpoints pour interagir avec l'application.

- **`BankAccountRestAPI`** : Endpoints pour gérer les comptes et les transactions. Permet des fonctionnalités comme la création de comptes et l'exécution de transferts.
- **`CustomerRestController`** : Gère les opérations CRUD pour les clients (ajout, modification, suppression, recherche).
- **`UserController`** : Gère les utilisateurs, y compris l'authentification et la gestion des rôles.

Swagger UI est activé pour la documentation API et est accessible via :  
`http://localhost:8085/swagger-ui.html`.

---

### **7. `com.jee.ebanking_backend.config`**
Ce package contient toutes les configurations de l'application.

- **Sécurité avec Spring Security** :
  - JWT utilisé pour authentifier les requêtes API.
  - Définition des rôles `USER` et `ADMIN`.
  - `PasswordEncoder` pour hasher les mots de passe.
  
- Exemple de configuration (type générique) :
  ```java
  @Configuration
  @EnableWebSecurity
  public class SecurityConfig extends WebSecurityConfigurerAdapter {
      @Bean
      public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
      }

      @Override
      protected void configure(HttpSecurity http) throws Exception {
          http.csrf().disable()
              .authorizeRequests()
              .antMatchers("/api/**").authenticated()
              .and()
              .httpBasic();
      }
  }
  ```

- **Configuration applicative** :
  Gestion des paramètres dans le fichier `application.properties`.

---

## **Points Techniques**

### **Base de Données**
- Base utilisée : **MySQL**
- Une base de données `E-BANK` est configurée avec Hibernate pour automatiser la gestion des schémas.
- Exemple de propriétés :
  ```
  spring.datasource.url=jdbc:mysql://localhost:3306/E-BANK?createDatabaseIfNotExist=true
  spring.datasource.username=root
  spring.datasource.password=
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  ```

### **Authentification**  
L'application utilise **JSON Web Tokens (JWT)** pour la sécurisation des endpoints. La clé secrète est définie dans les propriétés pour générer et valider les tokens.

### **Tests**
- Les tests sont définis dans le fichier `EBankingBackendApplicationTests.java`, prêts à être étendus pour inclure des tests de services ou d'intégration.

---

## **Prérequis**

- **JDK 17** ou supérieur.
- **MySQL** : Base de données conforme configurée dans `application.properties`.
- **Maven** : Gestionnaire de dépendances pour compiler et exécuter l'application.

---

## **Lancer l'Application**

1. Clonez ce dépôt via Git.
2. Configurez votre base de données locale avec MySQL.
3. Exécutez la commande suivante :
   ```bash
   mvn spring-boot:run
   ```
4. Accédez à l'application Swagger sur : `http://localhost:8085/swagger-ui.html`.

---

## **Contributeurs**

Ce projet a été développé et maintenu par **IDRISSI Yassine**.

---