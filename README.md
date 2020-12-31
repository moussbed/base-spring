# Base-spring

- **AOP : Aspect Oriented Programming, ou Programmation Orientée Aspect en Français**
     - L’un des deux concepts principaux de Spring (avec l’ Inversion de Contrôle)
     - Permet de rajouter des comportements à des classes ou des
       méthodes existantes
         - Ajouter de la sécurité
         - Ajouter la gestion des transactions
         - Ajouter du monitoring
     -  Il s’agit de problématiques transverses
         - Elles sont généralement techniques (infrastructure)
         - Elles sont plus rarement métier
         
     - **Spring AOP ou AspectJ**
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

- **Spring JDBC** 

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
         

- **Introduction aux transactions**
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
               -    ```java 
                          @Transactional
                          public void uneMethodeMetier() { 
                            // Unité de travail atomique
                          } 
                  ```      
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
                 - ``` <tx:jta-transaction-manager/> ```
        - **Utilisation des annotations**
            - L’annotation @Transactional peut être mise sur une classe (toutes les méthodes publiques sont transactionnelles) ou sur une méthode
            - Cette annotation a un certain nombre de paramètres : Isolation, Propagation, Timeout, readOnly...
        - **Variante : utilisation du XML**  
               
                < aop:config>
                  <aop:pointcut id="serviceBeans"
                   expression="execution(public * test.service.*(..))" /> 
                  <aop:advisor pointcut-ref="serviceBeans" advice-ref="txAdvice"/>
                </aop:config> 
                
                <tx:advice id="txAdvice"> 
                 <tx:attributes>
                  <tx:method name="find*" read-only="true"/>
                  <tx:method name="*"/> 
                 </tx:attributes>
                </tx:advice>
        
        - **Le TransactionTemplate**    
           - Si la configuration par annotations ou XML n’est pas
             suffisante, Spring propose une API
                - Elle utilise le système des Templates et des Callbacks que nous
                  avons déjà vus pour Spring JDBC
                   
                     ```java
                     transactionTemplate.execute(new TransactionCallbackWithoutResult() { 
                       protected void doInTransactionWithoutResult(TransactionStatus status){
                             try {
                                 insertionEnBase();
                                 miseAJourDeLaBase();
                     } catch (ExceptionMetier ex) { status. setRollbackOnly ();
                     } }
                     });  
                    ```
        - **Transactions et isolation**  
           - Dans la pratique, les transactions ne sont en réalité pas toujours bien isolées
           - Il y a quatre niveaux d’isolation,du plus sécurisé au moins sécurisé :
             - SERIALIZABLE
             - REPEATABLE READS
             - READ COMMITTED
             - READ UNCOMMITTED
           - Plus le niveau d’isolation est élevé, plus la base doit **locker** des ressources, et moins les performances sont bonnes
           - Le niveau d’isolation par défaut est configurable dans la base de données, sous Oracle il est à «READ COMMITTED»
           - Exemple 1 : non repeatable read
                   - Ce problème arrive lorsque l’on est en READ COMMITTED ou READ UNCOMMITTED
                            ![alt text](https://github.com/moussbed/base-spring/blob/main/transaction-non-repeatable-read.png?raw=true)
           - Exemple 2 : phantom read
                   - Ce problème arrive lorsque l’on est en REPEATABLE READS, READ COMMITTED ou READ UNCOMMITTED
                            ![alt text](https://github.com/moussbed/base-spring/blob/main/transaction-phantom-read.png?raw=true)
        
        - **La propagation des transactions**   
           - Que se passe-t-il quand une méthode transactionnelle en appelle une autre ?
                           ![alt text](https://github.com/moussbed/base-spring/blob/main/transaction-propagation.png?raw=true)
           
           - On peut configurer si l’on veut deux transactions différentes ou une seule transaction englobant les deux méthodes
              - **REQUIRED** : S’il y a déjà une transaction, l’utiliser. Sinon, en créer une
                    nouvelle. C’est le mode par défaut.
              - **REQUIRES_NEW** : Crée toujours une nouvelle transaction. S’il y en a déjà
                une, la suspend. 
              - **NESTED** : Peut faire une transaction imbriquée, si cela est supporté par le
                gestionnaire de transaction.   
              - **MANDATORY** : Une transaction doit déjà exister. Sinon, lance une
                Exception. 
              - **SUPPORTS** : Si une transaction existe déjà, l’utilise. Sinon, n’utilise pas de
                transaction
              - **NOT_SUPPORTED** : N’utilise pas de transaction. Si une transaction existe
                déjà, la suspend.
              - **NEVER** : N’utilise pas de transaction. Si une transaction existe déjà, lance
                une Exception. 
           - Exemple
              - On peut configurer la transaction via l’annotation @Transactional : si elle est «read-only», spécifier un timeout ou un mode de propagation particulier
              ```java
                @Transactional(readOnly = true, timeout = 30, propagation = Propagation. REQUIRES_NEW)
                public void maMethodeMetier() { 
                  //
                }
                 
              ```
        - **Les transactions XA** 
           - Les transactions XA permettent d’avoir une seule transaction en
             utilisant des ressources différentes
             - Deux bases de données
             - Une base de données et un serveur JMS
           - Pour fonctionner, il faut que ces ressources et le gestionnaire de
             transaction supportent les transactions XA
             - WebSphere, Weblogic proposent des gestionnaires de transaction XA
             - Avec Spring, vous pouvez configurer un gestionnaire de transaction XA
               externe : Atomikos, Bitronix
           - Avec Spring, c’est juste une configuration différente de l’infrastructure          
             - Aucun changement dans le code !
             - Nous vous déconseillons d’utiliser cette technologie
             - On peut généralement obtenir le même résultat de manière plus simple
               (sans faire de transaction distribuée)
             - De par son fonctionnement, elle est peu performante       
        
        - **Pattern «Open Transaction in View»** 
           - C’est un pattern très répandu dans les applications Web,
             aussi appelé «**Open Session In View**» (la session étant une
             session Hibernate)
             - Spring propose un listener de Servlet qui implémente ce pattern
           - Ce pattern est très pratique
             - Permet d’avoir une transaction ouverte tout le temps, y compris dans
               les pages Web
             - Règle les problèmes de «lazy loading» avec Hibernate
           - Nous le déconseillons parfois pour des raisons de
             performances
             - Il a tendance à laisser les transactions ouvertes trop longtemps
             - On arrive à terme à une requête HTTP == une transaction, et donc à
               une connexion en base de données. Difficile alors de monter en charge !
     
- **Les Tests**     
                            
    - **Introduction sur les tests**  
        - Les tests automatisés permettent d’améliorer la qualité du
          code
           - Garantissent le bon respect des règles métier
           - Facilitent le refactoring
        - Ils permettent également de coder plus efficacement
           - L’automatisation permet un feedback rapide
             - Permet de corriger un bug juste après l’avoir causé
             - Evite de polluer l’application et d’impacter les autres
               développeurs
        - Spring permet d’améliorer la conception des tests unitaires et
          propose un excellent support des tests d’intégration                       
                     
    - **Tests unitaires contre tests d’intégration**     
        - Il y a deux types de tests
           - Test d’un composant unique (métier ou technique), en isolation du
             reste des autres composants : ce sont les tests unitaires
             - Cela exclut l’utilisation de Spring
           - Test d’un ensemble de composants dans un environnement comparable à la production : ce sont les tests d’intégration
             - Cela inclut l’utilisation de Spring, sans doute avec une
               configuration d’infrastructure spécifique    
        - Les tests unitaires et les tests d’intégration ne sont pas
          exclusifs : il faut utiliser les deux conjointement pour bien tester
    
    - **Objectifs des tests**  
        - Les tests doivent couvrir un maximum de lignes de code de l’application   
           - Il ne s’agit pas de tester toutes les lignes de code, mais de bien tester les
             lignes de code importantes 
           - Si une méthode utilise des branchements conditionnels, il faut valider tous les cas possibles  
        - Ces tests doivent être rapides et automatisés   
           - Un jeu de test qui dure longtemps ne sera jamais exécuté par les développeurs : son utilité est donc nulle   
    
    - **L’intégration continue**   
        - L’utilisation d’un serveur d’intégration continue est essentielle
          pour exécuter ces tests
           - Aujourd’hui il n’y a plus de question à se poser : utilisez Jenkins !
             http://jenkins-ci.org/    
        - Ce serveur vous alerte si les tests ne fonctionnent pas
           - Vous évite d’updater votre projet si un de vos collègues a commité des bugs
        - **_Bonne pratique : toujours faire passer les tests unitaires avant
          de commiter_**  
           - Renforce l’argument que ces tests doivent être rapides
           - Certains IDE proposent cette fonctionnalité (ils refusent de commiter s’il y a
             des erreurs)
           - Certaines équipes vont plus loin : elles commitent dans un repository
             intermédiaire, et leur code n’est ensuite poussé dans le repository principal que si les tests passent
    
    - **Tests unitaires**  
        - Les tests unitaires permettent de tester une méthode en
          isolation du reste de l’application
           - Cela exclut Spring
        - Cependant, grâce à Spring, vous avez des Beans faciles à tester
           - L’injection de dépendance fait que les Beans Spring sont faciles à
             tester unitairement : il suffit de remplacer ces dépendances par des
             Stubs ou des Mocks 
           - L’AOP permet de n’avoir qu’une seule fonctionnalité métier par
             méthode
              - Pas de code technique gérant les transactions ou la sécurité
                mélangé au code métier     
                        
    - **Exemple de test «simple»**     
        - JUnit permet de lancer facilement toutes les méthodes marquées @Test
           - Maven les lance ensuite dans sa phase «test»
             ```java
                 package com.mb.spring;
                 
                 import com.mb.spring.models.Todo;
                 import org.junit.Test;
                 import static org.junit.Assert.*;
                 public class MyTest {
                 
                     @Test
                     public void testEquals(){
                         Todo todo1 = new Todo();
                         todo1.setCompleted(false);
                         Todo todo2 = new Todo();
                         todo2.setCompleted(false);
                         assertEquals(todo1.isCompleted(),todo2.isCompleted());
                 
                         Todo todo3 = new Todo();
                         todo3.setCompleted(true);
                 
                         assertNotSame(todo1.isCompleted(),todo3.isCompleted());
                     }
                 }
             ```                 
    - **Des Stubs ou des Mocks ?**   
        - En Français on confond les deux termes sous le nom de «bouchon»
        - Ils sont nécessaires pour tester les dépendances, en particulier celles injectées par Spring
        - Un **Stub** : une implémentation «**vide**» d’une dépendance  
           - Exemple : pour un DAO, faire une implémentation qui n’accède pas
             en base de données mais renvoie toujours les mêmes valeurs
        - Un **Mock** : une implémentation générée par une librairie
          spécialisée, qui la crée à la volée en fonction de l’interface à respecter         
   
    - **Astuce: comment injecter dans un champ** ?  
        - Pour tester unitairement un Bean Spring ayant des dépendances  
           - Il ne faut pas utiliser Spring (sinon ce ne serait plus un test)
           - Il faut donc injecter manuellement ses dépendances
        - Cette injection est évidente si l’on utilise l’injection par Setter ou par Constructeur
        - Pour l’injection par Champ, qui est de plus en plus populaire, Spring propose cet utilitaire :
            - ```java
                  ReflectionTestUtils.setField(todosService, "todoListsService" ,
                                                       todoListsService);
              ```
            -  Il injecte une variable «todoListsService» dans le champ nommé «todoListsService» du Bean «todosService»
    
    - **Exemple de Stub**   
        - Le Stub implémente la même interface que la dépendance injectée 
        - Le Stub peut être une classe anonyme (exemple ci-dessous), pour éviter de créer trop de fichiers   
        - Cela peut être également une vraie classe, afin de pouvoir le réutiliser sur plusieurs tests   
         ```java
            @Test
            public void testUserService1(){
              // Injection by constructor
              UserService userService = new UserServiceImpl(new UserRepository() {
                 @Override
                 public User getUserCurrent(String name) {
                    return new User().setName("Bedril");
                 }
              });
       
             assertEquals(userService.getCurrentUser().getName(),"Bedril");
       
           }
           
             @Test
             public void testUserService2(){
         
                 UserService userService = new UserServiceImpl2();
                 // Injection by Field
                 ReflectionTestUtils.setField(userService, "userRepository", new UserRepository() {
                     @Override
                     public User getUserCurrent(String name) {
                         return new User().setName("Bedril");
                     }
                 });
                 assertEquals(userService.getCurrentUser().getName(),"Bedril");
         
             }
        ```
    - **Exemple de test avec Mockito** 
        - confere le code git : https://github.com/moussbed/base-test-mockito/tree/main/src/test/java/com/inet/mockito/mockito 
    
    -  **Pourquoi utiliser des Mocks ?**   
        - Les Mocks sont aujourd’hui très populaires
          - Ils évitent d’avoir à coder des Stubs
          - Ils sont plus rapides à coder et à maintenir que des Stubs : on ne
            code que les méthodes nécessaires au test
          - Il est plus simple de les faire changer de comportement
        - Ils restent plus complexes à coder  
        - Il faut utiliser une librairie spécialisée
          - Mockito 
    
    - **Tests d’intégration**  
        - Les tests d’intégration incluent Spring
           - Normalement avec une application context réduit : uniquement un ensemble de classes que l’on veut tester    
           - Avec une configuration d’infrastructure spécifique : une base de données en mémoire ou une instance spécifique de la base de données cible
        - Spring propose une intégration à JUnit qui simplifie grandement ce type de configuration   
        
        - **Support des tests d’intégration dans Spring**   
           - SpringJUnit4ClassRunner permet d’intégrer Spring dans un test JUnit
           - L’annotation @ContextConfiguration permet alors de localiser la configuration de Spring et de lancer l’Application Context
           - On peut ainsi tester son application Spring avec JUnit, sans serveur d’applications 
              - C’est évidemment beaucoup plus rapide à exécuter que de déployer
                l’application sur un serveur d’applications
                ```java
                   @RunWith(SpringJUnit4ClassRunner.class) 
                   @ContextConfiguration(locations={"classpath*:/META-INF/spring/application-context-test.xml"}) 
                   public class IntegrationTest {
                    @Inject
                    private UserService userService;
                    @Test
                    public void createUser() { 
                       try {
                         userService.findUser("test_user");
                         fail("User already exists in the database."); 
                       } catch (ObjectRetrievalFailureException orfe) {
                            // User should not already exist in the database.
                       }
                       User user = new User();
                       user.setLogin("test_user");
                       user.setFirstName("First name");
                       userService.createUser(user);
                       User userFoundInDatabase = userServicef.indUser("test_user"); 
                       assertEquals("First name", userFoundInDatabase.getFirstName());
                    } 
                   }
                ```
        - **Astuce 1 : lancer l’application context une seule fois**  
           - Spring ne lance qu’un seul application context par classe
              - Toutes les méthodes de test d’une classe donnée utilisent la même
                instance
           - Cela permet d'accélérer les tests : sinon on lancerait beaucoup plus d’application contexts     
              - Cela ne doit pas avoir d’autre impact
                - En effet, vos Beans sont censés être thread safe        
        - **Astuce 2 : rollback des transactions dans les tests d’intégration**    
           - Par défaut, toute méthode de test annotée @Transactional va être rollbackée à la fin du test
              - Inutile de nettoyer la base de données après un test
              - Le test sera également plus performant
              - Le rollback n’est possible qu’à la condition que personne ne commite explicitement pendant le test !    
              
              ```java
                 @RunWith(SpringJUnit4ClassRunner.class) 
                 @ContextConfiguration(locations={"classpath*:/META-INF/spring/application-context-test.xml}") 
                 public class IntegrationTest {
                 @Inject
                 private UserService userService;
                 @Test
                 @Transactional
                 public void createUser() {
                     // Même code que précédemment
                 } }
                  
              ```
             
- **ORM (Object-Relational Mapping)**     

   - **Introduction à l’ORM**
        - Cette technologie permet de mapper automatiquement des objets sur des tables
          - Cela facilite le développement
          - Cela donne une architecture logicielle de meilleure qualité
        - Il existe de nombreuses solutions d’ORM
          - JPA n’est qu’une API
          - EclipseLink en est l’implémentation officielle
          - Hibernate en est l’implémentation (de loin) la plus populaire
          
   - **Qu’apporte une solution d’ORM ?**    
       - Une solution d’ORM permet de se concentrer sur des objets
         Java (souvent appelés «objets de domaine», pour le
         domaine métier)
         - Le code SQL pour créer/sélectionner/mettre à jour/effacer ces
           données est automatiquement généré
         - Il n’y a plus de dépendance avec une base de données spécifique
           (différences dans le langage SQL)  
         - Elle fournit généralement des mécanismes avancés de cache et de
           chargement des données qui font qu’elle est au final plus performante que du SQL codé «à la main»
   
   - **Les problèmes soulevés par l’ORM**
       - Une technologie de mapping est nécessaire car il y a des
         différences fondamentales entre une table et un objet
         - Il n’y a pas d’héritage ou de polymorphisme en base de données
         - La gestion des associations est différente (one-to-many et many-to-
           many)
         - De nombreux types de données sont différents (par exemple il y a de
           nombreuses façons de stocker une String en base de données)
         - Les contraintes de validation ne sont pas gérées de la même
           manière
       - Une solution d’ORM a pour but d’alléger ou de simplifier ces
         problèmes                      
  
   - **Quelle implémentation choisir ?**  
       - Hibernate est de très loin l’implémentation la plus répandue
         - Hibernate a largement fait ses preuves en termes de qualité et de
           performance
         - Il faut avoir une très bonne raison pour ne pas prendre Hibernate
       - **Privilégier l’API JPA**
         - C’est ce que recommande également l’équipe Hibernate ! 
         - Lorsque JPA n’est pas suffisant, on peut toujours compléter avec l’API spécifique Hibernate         
   
   - **Mapping d’une entité simple** 
       - ```java
         @Entity
         @Table(name = "t_todo")
         public class Todo implements Serializable {
          @Id
          @Column(name = "id") 
          private String todoId;
          @Column(name = "creation_date") 
          private Date creationDate;
          private String description;
          private int priority;
             // getters et setters
         }
         ```     
       - Ce mapping utilise uniquement des annotations JPA
       - Les annotations sont :
          - Au niveau de la classe, pour la mapper sur une table donnée
          - Au niveau des champs, qui correspondent aux colonnes de la table
       - La configuration est implicite
          - Les champs qui n’ont pas d’annotations sont par défaut mappés sur
            des colonnes ayant le même nom qu’eux
          - Si le nom de la colonne est différent, on peut le paramétrer avec l’annotation @Column  
          - Si le champ ne doit pas être mappé, il faut le marquer avec @Transient
       - Dans le cas le plus simple, il suffit d’annoter la classe avec @Entity et définir le champ contenant la clef primaire avec @Id
   
   - **Utilisation de ce mapping**    
       - 
       ```java
            @Service
            @Transactional
            public class TodosServiceImpl implements TodosService {
               @PersistenceContext
               private EntityManager em;
               public void createTodo(Todo todo) {
                  Date now = Calendar.getInstance().getTime();
                  todo.setCreationDate(now);
                  em.persist(todo);
              }
               public Todo findTodo(String todoId) {
                  return em.find(Todo.class, todoId);
              }
               public Todo updateTodo(String todoId, String description) {
                  Todo todo = em.find(Todo.class, todoId);
                  todo.setDescription(description);
              }
               public void deleteTodo(String todoId) {
                  Todo todo = em.find(Todo.class, todoId);
                  em.remove(todo);
              } 
            }
        ```            
       - Le PersistenceContext est la classe principale, qui permet de requêter, créer ou supprimer des objets en base de données
       - La persistance est transparente : dès qu’un objet est géré par le PersistenceContext, ses modifications seront automatiquement répercutées en base de données à la fin de la transaction
       - Pour mettre à jour un objet, il suffit donc d’appeler ses setters, et d’attendre la fin de la transaction
   
   - **Le cache de premier niveau**
       - Hibernate stocke en fait tout objet lu depuis la base dans un cache de premier niveau
         - Ce cache correspond à la transaction en cours
       - Si vous faites deux fois un «**find**» sur la même instance, seul le premier appel lancera une requête SQL  
         - Le deuxième appel le lira dans le cache de premier niveau
         - Cela limite considérablement les risques de «**NON REPEATABLE READS**» que nous avons abordés dans le chapitre sur les transactions
       - À la fin de la transaction, Hibernate va «**flusher** (Demande la mise à jour immédiate de qui est dans le cache )» ce cache
         - Il va calculer toutes les modifications de l’objet qu’il doit répercuter en base
         - Il va alors exécuter toutes les requêtes à la suite
         
   - **Mapping many-to-one et one-to-many**    
       - JPA permet également de gérer les relations entre les objets
         - Un objet qui contient une collection d’autres objets
         - Cette relation est mappée comme une relation one-to-many en base de données, utilisant une foreign key  
         - Exemple de mapping many-to-one
           - ```java
              @Entity
              public class Todo implements Serializable { 
               @Id
               private String todoId; 
               @ManyToOne
               private TodoList todoList; 
               private Date creationDate; 
               private String description; 
               // getters et setters
            ```
         - Ce mapping se configure également avec une annotation sur un champ
         - Nous avons deux objets mappés sur deux tables, et une annotation qui correspond à la foreign key joignant ces deux tables
         - Il suffit de modifier l’objet lié (todoList dans l’exemple précédent) pour modifier la foreign key
            ```java
              public void createTodo(String listId, Todo todo) {
                Date now = Calendar.getInstance().getTime(); 
                todo.setCreationDate(now);
                TodoList todoList = todoListsService. findTodoList(listId); 
                todo.setTodoList(todoList);
                em.persist(todo); 
              }     
            ```
   - **Les associations bi-directionnelle**
       - En fonction du métier, certaines associations sont en fait bi-
         directionnelles
         - L’objet parent a accès à une collection d’enfants
         - Chaque enfant a accès à l’objet parent
       - Il faut prévenir JPA que le mapping est bi-directionnel
       - Nous travaillons toujours en Java : il faut donc traiter cela
         également côté Java
       - Faire tous les mappings bi-directionnels est une mauvaise
         pratique
         - Cela a un impact sur les performances
         - Cela signifie également que vous avez mal modélisé votre métier
       - Exemple de mapping bi-directionnel
         - ```java
             @Entity
             public class Todo implements Serializable { 
              @Id
              private String todoId;
           
              @ManyToOne
              private TodoList todoList; 
           
             }
           
             @Entity
             public class TodoList implements Serializable { 
              @Id
              private String listId;
           
              @OneToMany(mappedBy = "todoList")
              private Set<Todo> todos = new HashSet<Todo>();
             
             }
            
           ```
       - Utilisation du mapping bi-directionnel
         - ```java
               public void createTodo(String listId, Todo todo) {
                 TodoList todoList = todoListsService. findTodoList(listId); 
                 todo.setTodoList(todoList); 
                 em.persist(todo); 
                 todoList.getTodos().add(todo);
               }
               public void deleteTodo(Todo todo) { 
                 TodoList todoList = todo.getTodoList(); 
                 Set<Todo> todos = todoList.getTodos(); 
                 todos.remove(todo);
                 em.remove(todo);
              }
           ```         
   - **Les méthodes equals() et hashCode()**  
       - L’utilisation de Set renforce la nécessité de bien implémenter les méthodes equals() et hashCode()
       - En effet, un Set utilise le **hashCode** pour savoir si un objet est déjà présent ou non dans la Collection 
         - Si le hashCode est mauvais, on peut remplacer par erreur un autre objet de la Collection
         - Si le hashCode est inexistant, Java va alors utiliser l’adresse mémoire :
           il sera alors possible d’insérer deux instances du même objet, ce qui sera très certainement problématique en base de données (violation de la Clef Primaire)          
           
   - **Mapping many-to-many**   
       - JPA permet également de modéliser des relations many-to-many 
         - Exemple : **un utilisateur possède plusieurs listes, une liste peut être
           possédée par plusieurs utilisateurs**
         - C’est un cas métier relativement classique lorsqu’on code en Java
         - Ce type de relation n’existe pas en base de données : il impose d’utiliser une table de jointure
       - La table de jointure est automatiquement gérée par JPA
         - Elle possède deux foreign keys : une vers chaque table contenant les
           entités
       - Exemple de mapping many-to-many
         - ```java
              @Entity
              public class User implements Serializable { 
               @Id
               private String login;
           
               @ManyToMany
               private Set<TodoList> todoLists = new HashSet<TodoList>(); 
           
              } 
              @Entity
              public class TodoList implements Serializable { 
               @Id
               private String listId; 
           
               @ManyToMany(mappedBy = "todoLists")
               private Set<User> users = new HashSet<User>();
           
              }
           ```      
       - Utilisation du mapping many-to-many
         - ```java
               public void createTodoList(TodoList todoList) { 
                 User user = userService.getCurrentUser(); 
                 todoList.getUsers().add(user); 
                 em.persist(todoList); 
                 user.getTodoLists().add(todoList);
              }
              public void deleteTodoList(String listId) {
                 TodoList todoList = em.find(TodoList.class, listId); 
                 for (User user : todoList.getUsers()) {
                  user.getTodoLists().remove(todoList); 
                 }
                 em.remove(todoList); 
             }
            ```    
   - **Programmation défensive**
       - En Java, quand nous avons une relation bi-directionnelle, nous devons traiter les deux côtés de la relation
       - Dans les exemples précédents, c’est la couche service qui gère cela 
          - Mais un développeur peut la contourner, en utilisant directement les
            objets de domaine
          - Il est donc recommandé de gérer les bi-directions au niveau des
            entités  
           - ```java
               @Entity
               public class User implements Serializable {
               // ...
               protected void setTodoLists(Set todoLists) { 
                 this.todoLists = todoLists;
               }
               public void addTodoList(TodoList todoList) { 
                  this.getTodoLists().add(todoList); 
                  todoList.getUsers().add(this);
               }
               public void removeTodoList(TodoList todoList) { 
                   this.getTodoLists().remove(todoList); 
                   todoList.getUsers().remove(this);
               }
              ```