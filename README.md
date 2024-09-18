# Recipify
Projet final de la formation développeur fullstack Java.

## Objectif

Créer une application web permettant aux utilisateurs de générer des recettes à partir des ingrédients disponibles dans leur réfrigérateur. L'application doit inclure des fonctionnalités pour ajuster les recettes en fonction du nombre de couverts, des calories par plat, et d'autres critères diététiques ou d'allergies.

## Fonctionnalités principales

- **Gestion des ingrédients disponibles** : l'utilisateur peut entrer les ingrédients disponibles dans son réfrigérateur. L’application suggère des recettes en fonction des ingrédients saisis. Un admin peut créer, supprimer, modifier des recettes.
- **Ajustement du nombre de couverts** : chaque recette doit pouvoir être ajustée en fonction du nombre de personnes à servir.
- **Calcul des calories par plat** : chaque recette doit afficher une estimation du nombre de calories par portion, et ajuster ce nombre en fonction des portions choisies.
- **Filtre pour restrictions alimentaires** : l’utilisateur peut spécifier des allergies ou des préférences diététiques (végétarien, sans gluten, etc.), les suggestions de recettes seront filtrées en conséquence.
- **Gestion d'un profil utilisateur** : Les utilisateurs peuvent créer un compte avec
   -  des informations personnelles (allergies, préférences alimentaires, etc.). 
   -  la sauvegarde des recettes préférées. 
   -  la gestion admin des users ( CRUD ).

## Fonctionnalités facultatives
Les utilisateurs peuvent créer des recettes. Ces recettes seront par défaut en privé. Afin d'être visibles par tous, ces recettes doivent être validées par un admin.

## Technologies imposées
Front-end : Angular
Back-end : SpringBoot

## Contraintes techniques
L'application doit être responsive.

## Délai
Les étudiants ont 7 jours ouvrés pour réaliser le projet, en groupe de 3 ou 4.

## Livrables
- Code source hébergé sur une plateforme de versionnement.
- Documentation : un fichier readme décrivant comment installer et utiliser l'application.
- Présentation du projet devant le reste de la classe, démontrant les fonctionnalités clés.

## Organisation
- méthode agile + kanban
- daily meetings

## Prérequis

Avant de commencer, assurez-vous d'avoir installé [Docker](https://docs.docker.com/get-started/get-docker/)

## Installation

1. Clonez ce dépôt sur votre machine locale :

```bash
git clone https://github.com/JulienBrancourt/Recipify_app
```

2. Assurez-vous que Docker est bien installé et fonctionnel en exécutant :

```bash
docker --version
docker-compose --version
```

## Utilisation avec Docker Compose

Pour démarrer le projet, utilisez la commande suivante dans le répertoire racine du projet où se trouve le fichier docker-compose.yml :

```bash
docker-compose up --build
```

## Accès à l'application
- Front-end Angular : [http://localhost:4200](http://localhost:4200)
- Back-end Spring Boot : [http://localhost:8080](http://localhost:8080)
