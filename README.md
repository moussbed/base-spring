# Base-spring

- AOP : Aspect Oriented Programming, ou Programmation Orientée Aspect en Français
     - L’un des deux concepts principaux de Spring (avec l’ Inversion de Contrôle)
     - Permet de rajouter des comportements à des classes ou des
       méthodes existantes
         - Ajouter de la sécurité
         - Ajouter la gestion des transactions
         - Ajouter du monitoring
     -  Il s’agit de problématiques transverses
         - Elles sont généralement techniques (infrastructure)
         - Elles sont plus rarement métier
         
     - Spring AOP ou AspectJ
         - **Spring AOP utilise un mécanisme de proxy**
             - Ne fonctionne que sur des Beans Spring, et son utilisation est très simple
             - Est largement suffisant pour des besoins "normaux", ce qui fait que la très
               grande majorité des utilisateurs de Spring utilise Spring AOP
         - **AspectJ est une technologie nettement plus complète et complexe**
             - Repose sur une modification du bytecode des classes Java
             - Permet de faire des choses nettement plus compliquées : injecter des Beans
               Spring dans des classes standards, par exemple
             - Est plus performant en production (mais cela a un impact mineur dans la
               réalité : à comparer avec un accès base de données)  
               
         - **Spring AOP utilise le même "langage" que AspectJ, ce qui fait que
           l'on peut facilement migrer de Spring AOP vers AspectJ**  
           
     - Fonctionnement de Spring AOP 
         - Un proxy «enrobe» le Bean Spring
             - Il implémente la même interface, et peut ainsi le remplacer
             ![alt text](https://github.com/moussbed/base-spring/blob/main/proxy-aop.png?raw=true)
     - Fonctionnement des proxys
         - Ce sont normalement des proxys Java 
             - Technologie standard Java (introduite dans le JDK 1.3)
             - Aujourd'hui suffisamment performants (impact mineur)
             - Nécessitent l'utilisation d'une interface
             - L'utilisation d'interfaces est recommandée de toute manière (pour les
               tests et la souplesse de l'application)
         - Si vos classes n'implémentent pas d'interface
             - Spring AOP va utiliser CGLIB pour générer le proxy
             - CGLIB est une librairie Open Source qui permet de générer des
               classes à la volée
             - Aujourd'hui cette technologie est fiable, mais les proxys Java restent
               à privilégier  
               
     - Que se passe-t-il si on a plusieurs Aspects sur le même Bean ?
         - Exemple : une méthode est transactionnelle et sécurisée
         - Spring ne génère qu'un seul proxy
         - Spring va enchaîner ces Aspects
         - L'ordre de ces Aspects peut être paramétré avec l'interface org.springframework.core.Ordered ou l’annotation @Order      
     
     - Les concepts de l'AOP  
         - **Join point** : l'endroit où l'on veut qu’un aspect s'applique. Avec Spring AOP, il s’agit toujours d’une méthode (du fait de l'utilisation de proxy)
         - **Pointcut** : une expression, utilisant la syntaxe AspectJ, qui permet de sélectionner plusieurs Join points. Par exemple, «toutes les méthodes qui se nomment find()».             
         - **Advice** : le code que l'on veut rajouter. On peut ajouter ce code avant, après, autour de la méthode...
         - **Aspect** : Pointcut + Advice
         
     - Les types d’advices  
         - **Before advice** : s’exécute avant le Join point. S’il lance une
           Exception, le Join point ne sera pas appelé  
         - **After returning advice** : s’exécute après le Join point, si celui-ci s’est bien exécuté (s’il n’y a pas eu d'Exception)
         - **After throwing advice** : s’exécute si une Exception a été lancée
           pendant l’exécution du Join point
         - **After advice** : s’exécute après le Join point, qu’il y ait eu une
           Exception ou non 
         - **Around advice** : s’exécute autour du Join point. C’est l’advice le plus
           puissant.
     - Configuration des aspects
         - Plusieurs configurations sont possibles
             - En XML
             - En utilisant des annotations, dite méthode «@AspectJ»
         - La méthode @AspectJ est à privilégier
             - Plus simple à utiliser
             - Permet d’avoir des Pointcut et les Advice au même endroit
         - Pour pouvoir utiliser la méthode @AspectJ, ajouter dans votre configuration Spring :
              <aop:aspectj-autoproxy/>
              
     - Définition d’un aspect
         - Un Aspect est également un Bean Spring
             - Mais il ne peut pas y avoir d’Aspect sur un autre Aspect
             - On peut séparer le Pointcut de l’Advice, mais c’est plus lisible de tout
               mettre dans le même fichier         
                            ![alt text](https://github.com/moussbed/base-spring/blob/main/aspect-aop.png?raw=true)
     - Introduction au langage AspectJ
         - Exemple de pointcuts 
             - Exécution de toutes les méthodes publiques
                - _execution(public * *(..))_
             - Exécution de tous les getters
                - _execution(* get*(..))_
             - Exécution de tous les getters qui retournent des String
                - _execution(* get*(..))_
             - Exécution de toutes les méthodes de l’interface
               ExampleService
                - _execution(* example.ExampleService.*(..))_
             - Exécution de toutes les méthodes du package example.test
                - _execution(* example.test.*.*(..))_   
             - Exécution de toutes les méthodes du package example.test et de ses sous-packages
                - _execution(* example.test..*.*(..))_
             - Toutes les méthodes annotées avec @Transactional
                - _@annotation(org.springframework.transaction.annotation.Transactional)_      
             - Toutes les méthodes du Bean nommé «testService»
                - _bean(testService)_
             - Toutes les méthodes de tous les Beans dont le nom se termine par «Service»
                - _bean(*Service)_   
         - Les PointCuts peuvent être combinés
             - On peut nommer des PointCuts afin de les réutiliser
             - On peut les combiner avec les opérateurs logiques «&&»,
               «||» et «!»
                 ![alt text](https://github.com/moussbed/base-spring/blob/main/pointcut-combines-aop.png?raw=true)

         - Accès aux arguments des méthodes
             - Il est intéressant d’avoir accès aux arguments des méthodes sur lesquelles les Aspects s’appliquent
             - On peut les «binder» sur l’Advice
                              ![alt text](https://github.com/moussbed/base-spring/blob/main/advice-method-arguments.png?raw=true)

         - Accès au Join Point   
             - Pour accéder au Join Point, il suffit de passer l’objet en premier paramètre de l’Advice
             - Vous avez alors accès à :
                 - Args : les arguments passés à la méthode
                 - Signature : la signature de la méthode
                 - Target : le Bean sur lequel s’applique l’Aspect
                 - This : l’objet en cours (le proxy entourant le Bean)
                                ![alt text](https://github.com/moussbed/base-spring/blob/main/advice-joint-point.png?raw=true)

- Spring JDBC 

    - Pour lancer la BD :
         - > cd dataBase 
         - > docker-compose -f stack.yml up
    - **Abstraction relativement simple au-dessus de JDBC**
         - Gestion automatique de la DataSource(ouverture et fermeture)
         - Gestion automatique des Exceptions
         - Classes utilitaires simples, utilisant des Templates et des Callbacks    
    - **Spring JDBC est pratique pour**     
         - Faire des CRUD simples
         - Avoir un accès «bas niveau» à la base de données
    - **Spring JDBC peut être utilisé conjointement à Hibernate/JPA, qui fournit une solution bien plus complète d’accès aux bases de données relationnelles**     
         

- Introduction aux transactions
    - Les transactions sont typiquement gérées par une base de données relationnelles
    - Une transaction est normalement **ACID**
         - **A**tomique
         - **C**ohérente (**C**onsistant)
         - **I**solée
         - **D**urable
    - En Java, nous pouvons les gérer via des APIs simples, par exemple en JDBC ou avec JTA 
    
    - **Utilité des transactions**
         - Il est primordial de gérer les transactions si on veut avoir des
           données de qualité
              - Les transactions permettent de traiter un ensemble d’opérations
                comme une seule opération
              - Pas de données incohérentes dans la base  
         - Les transactions permettent également d’avoir de meilleures
           performances
              - Il vaut mieux faire 10 requêtes dans une transaction que de faire 10
                requêtes dans 10 transactions
              - C’est d’ailleurs une des raisons de leur utilisation dans les batchs
    - **Exemple d’une transaction**     
        - 3 requêtes : 1 lecture, 2 écritures
        - 1 seule transaction (matérialisée par la flèche)
        - Soit les 2 modifications sont appliquées, soit aucune n’est appliquée
              ![alt text](https://github.com/moussbed/base-spring/blob/main/transaction.png?raw=true)
    - **Les transactions en Java**
        - En JDBC 
              ![alt text](https://github.com/moussbed/base-spring/blob/main/transaction-jdbc.png?raw=true)
        - Avec JTA (Java Transaction API)
              ![alt text](https://github.com/moussbed/base-spring/blob/main/transaction-jta.png?raw=true)

    - **Spring Transactions**   
        - **Spring propose une couche d’abstraction**
             - Gère les transactions JDBC, Hibernate, JTA etc... de manière homogène
             - Permet de simplement configurer ses transactions : utilisation d’annotations ou d’XML, sans utilisation obligatoire de code
        - **Cela permet d’avoir une meilleure architecture**
             - Les transactions sont déclarées dans la couche métier (service), et
               non dans la couche d’accès aux données (repository / DAO)
             - Les transactions ne dépendent pas d’une technologie particulière d’accès aux données (JDBC)                                      
        - **Utilisation simple avec Spring**     
             - Configurer un gestionnaire de transaction
                -        <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
                            <property name="dataSource" ref="dataSource"/>
                         </bean>
             - Dire à Spring que l’on veut utiliser les annotations
               -        <tx:annotation-driven/>    
             - Utiliser les annotations
               -    `@Transactional
                    public void uneMethodeMetier() { 
                    // Unité de travail atomique
                    }   `      
        - **Fonctionnement dans Spring**  
            - Spring fournit un Aspect spécialisé
               - Le Point Cut est sur les méthodes annotées @Transactional
               - L’Advice est de type Around, et ajoute la gestion des transactions
                 autour des méthodes annotées          
            - C’est le fonctionnement que nous avons vu dans le chapitre
              sur Spring AOP, avec la génération d’un proxy
               - Ne fonctionne que sur les Beans Spring
               - Ne fonctionne que sur les méthodes publiques
               - Ne fonctionne pas à l’intérieur d’un même Bean
     
        - **Configuration d’un gestionnaire de transaction**   
            - Le gestionnaire de transaction est une classe fournie par
              Spring
               - Il fait partie de l’infrastructure
               - Il est spécifique à la technologie utilisée
               - Hors JTA, il a besoin d’une Data Source pour être configuré
               - Par convention, il possède l’id «transactionManager»
            - Si vous êtes dans un serveur d’applications (Websphere, Weblogic...), Spring peut retrouver automatiquement le gestionnaire de transactions de ce serveur (utilisant l’API JTA) :
               -  <tx:jta-transaction-manager/>
                             