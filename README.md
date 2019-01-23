# ExempleSocketIOClientServeurJava
pour les cours de projets en Licence, exemple de client serveur
Note : le serveur étant vexé quand on trouve le résultat, il se contente de se (ren)fermer :-)

Les différentes étapes : 
* branche **master**
    * tag étape1 : handshake, un serveur est lancé, un client ce connecte et c'est fini
    le client et le serveur écoute des évènements spéciaux : connexion, déconnexion
    **il y a deux projets java : serveur et client**
    * tag étape2 : après connexion du client, le serveur envoie une question au client (sans param), le client répond (42), et c'est fini
    Le serveur reçoit un Integer (conversion automatique par SocketIO-Server)
    * tag étape3 : une boucle est installée pour que le client réponde tant qu'il n'a pas trouvé
    Le serveur envoie un paramètre avec la question : un boolean, le client reçoit un boolean (et non pas un JSON)
    Si le client répond n'importe quoi, cela peut boucler indéfiniment. 
    * tag étape3.5 : le client, après connexion, envoie une identification (nom, niveau). Seulement après le serveur envoie la 1re question
    le client envoie un JSON, le serveur exploite une _Identification_, certaine conversion se font sur la base de l'introspection : analyse des propriétés (getter/setter)
    * tag étape4 : le serveur renvoie en plus de l'indication plus grand (true) ou plus petit (false), il renvoie aussi une liste de tous les coups déjà joué. La liste est transformée en tableau JSON, le client doit refaire la liste. C'est illustré, mais non utilisé
    * tag étape5 : réorganisation du code pour factoriser le code commun : 
    ** il n'y a plus qu'un seul projet maven avec 3 modules**
    **il faut utiliser _mvn install_**
    * tag étape5.1 : un lanceur (nouveau module) est ajouté pour exécuter en même temps le client et le serveur
    un peu de javadoc est ajouté (pom.xml parent et classe _Coup_) (utilisation avec _mvn site_)
* branche **android**

les dépendences maven côté serveur : 
```
<dependencies>
	<dependency>
     		 <groupId>com.corundumstudio.socketio</groupId>
     		 <artifactId>netty-socketio</artifactId>
     		 <version>1.7.17</version>
  	</dependency>
	<dependency>
		<groupId>io.netty</groupId>
		<artifactId>netty-transport</artifactId>
		<version>4.1.32.Final</version>
	</dependency>
  	<dependency>
     		 <groupId>org.slf4j</groupId>
     		 <artifactId>slf4j-simple</artifactId>
     		 <version>1.7.25</version>
  	</dependency> 
</dependencies>
```

les dépendences maven côté client
```
<dependency> 
    <groupId>io.socket</groupId>
    <artifactId>socket.io-client</artifactId>
    <version>1.0.0</version>
</dependency>   
```
