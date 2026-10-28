# Projekt

## Applikation starten mit: 
backend: ```mvn spring-boot:run```       
frontend: ```npm run build```
          ```npm start```


## Anforderungen:
- Es sollen Unit / Component Tests sichtbar sein
  - Unit Tests
    - RecipeService
    - IngredientEntityMapper
    - RecipeEntityMapper
  - Component Tests
    - RecipeControllerTest
    - API-Tests (Postman)

- Ein Mocking Framework soll verwendet werden
  - JUnit
  - Mockito
  - PostMan

- Code Coverage soll einsehbar sein
  - Verwendung von JaCoCo als Testcoverage tracking Tool. 
  - Nach Terminal Befehl im Backend Ordner: ```mvn test```
    - Mit Pipeline nach jedem Commit automatisiert 
  - Einsehbar in [index.html](recipe-planner-backend/target/site/jacoco/index.html)
    - Webseite in dem die Coverage der einzelnen Klassen bis zu den Methoden einsehbar sind
  - HTML-Coverage-Report (Zeilen-Coverage, Methoden-Coverage, übersicht über alle Klassen)

- Automatisiert sollen in irgendwelcher Form Test Reports dargestellt werden (done)
  - Macht Maven automatisch unter [surefire-reports](recipe-planner-backend/target/surefire-reports)
  - dort findet man für jede Testklasse ein eigenes txt-file mit einem Test-Report
  - Testreport zeigt:
    - Welche Tests wurden ausgeführt
    - Welche Tests sind fehlgeschalgen
    - Stacktraces
    - Laufzeit
    - Zusammenfassung aller Testklassen

- TDD soll mal ausprobiert werden
  - Exception/ feherverhalten/ rückmeldungen verbessern
    - Methode in RecipeService gibt wenn das Rezept nicht existiert null zurück
      - Idee Methode soll eine Java NoSuchElementException werfen die klar macht was der fehler ist. 
    - Http Status beim Post addRecipe soll Create 201 und nicht OK 200 zurückgeben
    - Http Post soll Inhalte validieren 

- Es sind aktiv pro Team Member 3 Pull Requests einzusehen 
  - Diese Anforderung haben wir zuerst übersehen da uns nicht bewusst war das bei github ein PullRequest das gleiche ist wie ein Merge request
    - Wir haben anfangs auf dem gleichen Branche gearbeitet und konnten so überhaput keine merge requests erstellen
    - Nachdem uns das aber bewusst war haben wir natürlich unsere eigenen featuere-Branches erstellt von denen wir dann Pull Requests eröffnen können.

- Es soll eine kleine Dokumentation erstellt werden
  - Kurze Planung (ohne GANT)
  - Grobe Architektur soll visualisiert werden
  - Soll ein kleines Test Konzept vorweisen
  - Kleine Reflexion über TDD und Code Reviews