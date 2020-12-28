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

               