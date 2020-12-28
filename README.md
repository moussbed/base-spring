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
                    