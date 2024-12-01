# Employees Management : Spring-Boot-Thymeleaf-CRUD-App

# les opérations de CRUD utilisées
<img src="https://github.com/user-attachments/assets/c729bb62-24c3-49c7-92ed-5dda36e644d5"></img>
<img src="https://github.com/user-attachments/assets/4351885c-1e08-47eb-a638-da57fbec0353"></img>
<img src="https://github.com/user-attachments/assets/c590e49d-bbe8-4a17-9d38-647a3eaf16c1"></img>


## Node.js 
- C'est un environnement d'exécution JavaScript côté serveur qui permet de créer des applications web  rapide et évolutives.
- Il permet d'exécuter des commandes comme ```ng serve``` pour démarre le serveur de développement Angular, compiler les fichiers ...

**Pour vérifier si Node.js est installé sur votre machine, ouvrez un terminal ou une invite de cmd et tapez la cmdsuivante: ```node -v```**

## npm (Node Package Manager)
- C'est un gestionnaire de paquets pour JavaScript qui permet d'installer, de gérer et de partager des bibliothèques et des dépendances pour les projets Node.js
- npm est utilisé pour installer les dépendances Angular (comme ``` @angular/core``` , ```rxjs``` ...) ...

**Pour vérifier si npm est installée sur votre machine, utilisez la cmdsuivante: ```npm -v```**

## Angular CLI (Command Line Interface)
C'est un outil permettant de créer, développer, tester et déployer des applications Angular facilement via des commandes.

**Pour vérifier s'il est déjà installé: ```ng version```**

## NVM (Node Version Manager)
C'est un outil permettant de gérer plusieurs versions de Node.js sur une même machine.

**Pour vérifier s'il est installé, exécutez la commande suivante dans un terminal : ```nvm --version```**


# Mettre à jour un projet ancien
- Le projet utilise TypeScript ```~3.9.5``` et Angular CLI ```~10.0.7```
- Ces version sont généralement compatibles avec **Node.js 12 ou 14**
- Je vais utiliser **Node.js 14**
- L'exécution de la cmd: ```node -v``` m'a donné **v20.14.0**
- Pour installer **la version 14 de Node.js** j'ai utilisé la cmd: ```nvm install 14```
- Et pour passer à cette version , j'ai utilise la cmd : ```nvm use 14```
- Pour vérifier la version utilisée: ```node -v``` , cela m'a affiché : **v14.21.3**
- Ensuite je supprime les dépendances existantes, en supprimant le dossier ```node_modules``` et éventuellement le fichier ```package-lock.json``` en utilisant les cmds suivantes:
```rm -rf node_modules```
```rm package-lock.json```
  Cette suppression permet de repartir sur une base propre avant de réinstaller les dépendances, surtout qu'on a changé la version de Node.js.
- Une foisces fichiers supprimés, on peut maintenant réinstaller toutes les dépendances en exécutant : ```npm install``` pour recréer le dossier ```node_modules``` et le fichier ```package-lock.json``` avec des versions compatibles.




