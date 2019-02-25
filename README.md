# ExempleSocketIOClientServeurJava

Pour cloner uniquement cette branche (indépendante et indépendemment des autres) : 
```
git clone --single-branch --branch autreExemple https://github.com/PhilippeRenevierGonin/ExempleSocketIOClientServeurJava.git
```

Utilisation : 
```
# dans le dossier où vous avez cloner le projet
mvn clean install
cd 7w
mvn exec:java
```

Pour les cours de projets en Licence, exemple de client serveur (**sans tests**, pour se concentrer sur le découpage client-serveur et les fonctionnalités intégrées itérativement) : cette branche autreExemple, montre des étapes pour la création d'un début de jeu pour un jeu, 7wonders. 
Pour cette mise en place, les étapes sont : 
   * création d'une **simple connexion** avec un joueur et une partie : https://github.com/PhilippeRenevierGonin/ExempleSocketIOClientServeurJava/commit/32fd702172c0587e0c2e6ae09215754ac382daaf
       * une configuration est partagée à travers des constantes (classe "static")
       * mises en place des modules maven : 
          * le module parent
          * le module donnees (commun au client et au serveur)
          * le module moteur (le serveur)
          * le module joueur (le client)
          * le module lanceur pour exécuter des parties
   * on passe à **4 joueurs** : https://github.com/PhilippeRenevierGonin/ExempleSocketIOClientServeurJava/commit/76719e3df2eed9a705f2828d57b73a7729f36a8f
       * le serveur mémorise les joueurs (et leur socket) dans la classe Participant
   * Premier message : à la connexion les joueurs, **les joueurs s'identifient** : https://github.com/PhilippeRenevierGonin/ExempleSocketIOClientServeurJava/commit/1aa7f8b545ab8c3984c7c42ccc8088a150ae5fa3
       * le nom des messages est également partagé à travers des constantes
       * le joueur n'envoie que son nom, juste une String
       * le serveur doit retrouver le Participant correspondant à la socket émitrice pour en changer le nom
   * quand les 4 joueurs sont identifiés, **le moteur démarre une partie** : https://github.com/PhilippeRenevierGonin/ExempleSocketIOClientServeurJava/commit/9cc33462cca2e79ee48a9bfaeba4c2ae4311d20e
       * une merveille (une donnée) est envoyé par le moteur à chaque client
       * la merveille d'un joueur est mémorisé côté moteur dans la classe Participant
       * le moteur envoie une main (7 cartes) 
       * La main d'un joueur est mémorisé côté moteur dans la classe Participant
   * **le client joue une carte** (la 1re de la main) après réception de la main : https://github.com/PhilippeRenevierGonin/ExempleSocketIOClientServeurJava/commit/0d5d668cad368e6de4e8b72e85c47bfdc88cc272
        * le client renvoie la carte choisie
        * le serveur l'enlève de la main après avoir identifier le Participant correspondant
        * l'exemple s'arrête là
   
