# ExempleSocketIOClientServeurJava

Pour cloner uniquement cette branche (indépendante et indépendemment des autres) : 
```
git clone --single-branch --branch autreExemple https://github.com/PhilippeRenevierGonin/ExempleSocketIOClientServeurJava.git
```

Pour les cours de projets en Licence, exemple de client serveur (**sans tests**, pour se concentrer sur le découpage client-serveur et les fonctionnalités intégrées itérativement) : cette branche autreExemple, montre des étapes pour la création d'un début de jeu pour un jeu, 7wonders. 
Pour cette mise en place, les étapes sont : 
   * mises en place des modules maven : 
       * le module parent
       * le module donnees (commun au client et au serveur)
       * le module moteur (le serveur)
       * le module joueur (le client)
       * le module lanceur pour exécuter des parties
   * création d'une **simple connexion** avec un joueur et une partie
       * une configuration est partagée à travers des constantes (classe "static")
   * on passe à **4 joueurs**
       * le serveur mémorise les joueurs (et leur socket) dans la classe Participant
   * Premier message : à la connexion les joueurs, **les joueurs s'identifient**
       * le nom des messages est également partagé à travers des constantes
       * le joueur n'envoie que son nom, juste une String
       * le serveur doit retrouver le Participant correspondant à la socket émitrice pour en changer le nom
   * quand les 4 joueurs sont connectés, **le moteur démarre une partie** 
       * une merveille (une donnée) est envoyé par le moteur à chaque client
       * la merveille d'un joueur est mémorisé côté moteur dans la classe Participant
       * le moteur envoie une main (7 cartes) 
       * La main d'un joueur est mémorisé côté moteur dans la classe Participant
   * **le client joue une carte** (la 1re de la main) après réception de la main
        * le client renvoie la carte choisie
        * le serveur l'enlève de la main après avoir identifier le Participant correspondant
        * l'exemple s'arrête là
   
